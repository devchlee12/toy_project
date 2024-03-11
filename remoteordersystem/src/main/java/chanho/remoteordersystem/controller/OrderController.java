package chanho.remoteordersystem.controller;

import chanho.remoteordersystem.Service.*;
import chanho.remoteordersystem.domain.CustomerOrder;
import chanho.remoteordersystem.domain.Product;
import chanho.remoteordersystem.domain.Seller;
import chanho.remoteordersystem.dto.CustomerOrderHistoryDto;
import chanho.remoteordersystem.dto.EventPayload;
import chanho.remoteordersystem.dto.OrderHistoryDto;
import chanho.remoteordersystem.dto.OrderSubmitForm;
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
        Long sellerId = tableService.getSellerIdByTableId(tableId);
        List<Product> menus = productService.getSellerAllProducts(sellerId);
        model.addAttribute("menus",menus);
        model.addAttribute("tid",tableId);
        return "order_menu";
    }

    @ResponseBody
    @PostMapping("/ordersubmit/{tableId}")
    public CustomerOrder orderSubmit(@PathVariable Long tableId, @RequestBody OrderSubmitForm orderSubmitForm,HttpServletRequest request){
        HttpSession session = request.getSession();
        List<Long> orders = (List<Long>)session.getAttribute("orders");
        if (orders == null){
            orders = new ArrayList<>();
        }
        orders.add(orderSubmitForm.getProductId());
        session.setAttribute("orders",orders);
        Long sellerId = tableService.getSellerIdByTableId(tableId);
        CustomerOrder customerOrder = new CustomerOrder(orderSubmitForm.getProductId(), tableId, sellerId);
        orderService.createOrder(customerOrder);
        Seller seller = sellerService.getSellerById(sellerId);
        sseEmitterService.broadcast(new EventPayload("update"),seller.getEmail());
        return customerOrder;
    }

    @GetMapping("/orderhistory")
    public String orderHistory(Principal principal, Model model){
        Seller seller = sellerService.getSellerByEmail(principal.getName());
        List<CustomerOrder> allOrderBySeller = orderService.getAllOrderBySeller(seller.getSellerId());
        List<OrderHistoryDto> orders = new ArrayList<>();
        for (CustomerOrder order : allOrderBySeller){
            log.info(order.toString());
            Product product = productService.getProductById(order.getProductId());
            orders.add(new OrderHistoryDto(product.getProductName(),product.getPrice(),order));
        }
        model.addAttribute("orders",orders);
        return "order_history";
    }

    @ResponseBody
    @GetMapping("/getorderlist")
    public List<String> getOrderList(HttpServletRequest request){
        HttpSession session = request.getSession();
        List<String> list = new ArrayList<>();
        List<Long> sessionOrders = (List<Long>)session.getAttribute("orders");
        if (sessionOrders == null){
            return list;
        }
        for (Long id : sessionOrders) {
            Product product = productService.getProductById(id);
            list.add(product.getProductName());
        }
        return list;
    }
}
