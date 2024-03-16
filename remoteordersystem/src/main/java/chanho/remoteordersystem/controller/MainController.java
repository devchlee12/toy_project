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
import chanho.remoteordersystem.utils.comparator.QueueDtoComparator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.sql.ast.tree.expression.Collation;
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
import java.util.Collections;
import java.util.List;

@Controller
@Slf4j
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
        //log.info("getSellerByEmail Start!");
        Seller seller = sellerService.getSellerByEmail(principal.getName());
        //log.info("getSellerByEmail End!");
        List<TableWithMenuDto> tableInfos = new ArrayList<>();
        List<QueueDto> queue = new ArrayList<>();

        //log.info("getAllTableBySeller Start!");
        List<SeatTable> allTableBySeller = seller.getTableList();
        //log.info("getAllTableBySeller End!");
        //log.info("테이블이랑 주문목록 Start!");
        for (SeatTable table : allTableBySeller){
            //log.info("table.getOrders Start!");
            List<CustomerOrder> allOrderByTable = table.getOrders();
            //log.info("table.getOrders End!");
            ArrayList<OrderAlarmDto> orderList = new ArrayList<>();
            for (CustomerOrder order : allOrderByTable){
                if (order.getServed() == true) continue;
                //log.info("order.getProduct Start!");
                Product product = order.getProduct();
                //log.info("order.getProduct End!");
                queue.add(new QueueDto(product.getProductName(),table.getTableName(),order.getOrderDate()));
                orderList.add(new OrderAlarmDto(product.getProductName(),order.getId()));
            }
            tableInfos.add(new TableWithMenuDto(table.getTableName(),orderList));
        }
        //log.info("테이블이랑 주문목록 End!");
        model.addAttribute("tableInfos", tableInfos);
        //정렬해야함
        QueueDtoComparator comp = new QueueDtoComparator();
        Collections.sort(queue,comp);
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
