package chanho.remoteordersystem.controller;

import chanho.remoteordersystem.Service.OrderService;
import chanho.remoteordersystem.Service.ProductService;
import chanho.remoteordersystem.Service.SellerService;
import chanho.remoteordersystem.Service.TableService;
import chanho.remoteordersystem.domain.CustomerOrder;
import chanho.remoteordersystem.domain.Product;
import chanho.remoteordersystem.domain.SeatTable;
import chanho.remoteordersystem.domain.Seller;
import chanho.remoteordersystem.dto.OrderAlarmDto;
import chanho.remoteordersystem.dto.OrderCompleteForm;
import chanho.remoteordersystem.dto.QueueDto;
import chanho.remoteordersystem.dto.TableWithMenuDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final SellerService sellerService;
    private final TableService tableService;
    private final OrderService orderService;
    private final ProductService productService;

    @GetMapping("/")
    public String root(){
        return "redirect:/main";
    }

    @GetMapping("/main")
    public String main(Principal principal, Model model){
        Seller seller = sellerService.getSellerByEmail(principal.getName());
        List<TableWithMenuDto> tableInfos = new ArrayList<>();
        List<SeatTable> allTableBySeller = tableService.getAllTableBySeller(seller.getSellerId());
        for (SeatTable table : allTableBySeller){
            List<CustomerOrder> allOrderByTable = orderService.getAllOrderByTableAndServed(table.getTableId());
            ArrayList<OrderAlarmDto> orderList = new ArrayList<>();
            for (CustomerOrder order : allOrderByTable){
                Product product = productService.getProductById(order.getProductId());
                orderList.add(new OrderAlarmDto(product.getProductName(),order.getOrderId()));
            }
            tableInfos.add(new TableWithMenuDto(table.getTableName(),orderList));
        }
        model.addAttribute("tableInfos", tableInfos);
        List<CustomerOrder> allOrderNotServed = orderService.getAllOrderNotServed();
        List<QueueDto> queue = new ArrayList<>();
        for (CustomerOrder co : allOrderNotServed){
            queue.add(new QueueDto(productService.getProductById(co.getProductId()).getProductName(),tableService.getTableById(co.getOrderTable()).getTableName()));
        }
        //정렬해야함
        model.addAttribute("queue",queue);
        return "seller_main";
    }

    @ResponseBody
    @PostMapping("/complete-order")
    public OrderCompleteForm completeOrder(@RequestBody OrderCompleteForm orderCompleteForm){
        orderService.completeOrder(orderCompleteForm.getOrderId());
        return orderCompleteForm;
    }
}
