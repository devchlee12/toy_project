package chanho.remoteordersystem.controller;

import chanho.remoteordersystem.Service.*;
import chanho.remoteordersystem.domain.CustomerOrder;
import chanho.remoteordersystem.domain.Product;
import chanho.remoteordersystem.domain.SeatTable;
import chanho.remoteordersystem.domain.Seller;
import chanho.remoteordersystem.dto.EventPayload;
import chanho.remoteordersystem.dto.OrderHistoryDto;
import chanho.remoteordersystem.dto.OrderSubmitForm;
import chanho.remoteordersystem.dto.ResponseDto.ResponseOrder;
import chanho.remoteordersystem.dto.ResponseDto.ResponseProduct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final TableService tableService;
    private final ProductService productService;
    private final OrderService orderService;
    private final SellerService sellerService;
    private final SseEmitterService sseEmitterService;

    @GetMapping("/ordermenu/{tableId}")
    public String orderMenu(@PathVariable Long tableId, Model model){
        Seller seller = tableService.getTableById(tableId).getSeller();
        List<Product> menus = seller.getProductList();
        List<ResponseProduct> collect = menus.stream()
                .map(product -> new ResponseProduct(product.getId(), product.getProductName(), product.getPrice()))
                .collect(Collectors.toList());
        model.addAttribute("menus",collect);
        model.addAttribute("tid",tableId);
        return "order_menu";
    }

    @ResponseBody
    @PostMapping("/ordersubmit/{tableId}")
    public ResponseOrder orderSubmit(@PathVariable Long tableId, @RequestBody OrderSubmitForm orderSubmitForm, HttpServletRequest request){
        SeatTable table = tableService.getTableById(tableId);
        Product product = productService.getProductById(orderSubmitForm.getProductId());
        CustomerOrder customerOrder = new CustomerOrder(product, table);
        orderService.createOrder(customerOrder);
        //세션에 주문 넣기
        HttpSession session = request.getSession();
        List<CustomerOrder> orders = (List<CustomerOrder>)session.getAttribute("orders");
        if (orders == null){
            orders = new ArrayList<>();
        }
        orders.add(customerOrder);
        session.setAttribute("orders",orders);
        //sse 브로드캐스트
        Seller seller = table.getSeller();
        sseEmitterService.broadcast(new EventPayload("update"),seller.getEmail());
        return new ResponseOrder(customerOrder.getId());
    }

    @GetMapping("/orderhistory")
    public String orderHistory(Principal principal, Model model){
        Seller seller = sellerService.getSellerByEmail(principal.getName());
        List<CustomerOrder> allOrderBySeller = orderService.getAllOrderBySeller(seller);
        List<OrderHistoryDto> orders = new ArrayList<>();
        for (CustomerOrder order : allOrderBySeller){
            Product product = order.getProduct();
            orders.add(new OrderHistoryDto(product,order));
        }
        model.addAttribute("orders",orders);
        return "order_history";
    }

    @ResponseBody
    @GetMapping("/getorderlist")
    public List<String> getOrderList(HttpServletRequest request){
        HttpSession session = request.getSession();
        List<String> list = new ArrayList<>();
        List<CustomerOrder> sessionOrders = (List<CustomerOrder>)session.getAttribute("orders");
        if (sessionOrders == null){
            return list;
        }
        for (CustomerOrder order : sessionOrders) {
            list.add(order.getProduct().getProductName());
        }
        return list;
    }
}
