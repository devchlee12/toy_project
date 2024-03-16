package chanho.remoteordersystem.Service;

import chanho.remoteordersystem.domain.CustomerOrder;
import chanho.remoteordersystem.domain.SeatTable;
import chanho.remoteordersystem.domain.Seller;
import chanho.remoteordersystem.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;

    @Transactional
    public void createOrder(CustomerOrder order){
        orderRepository.save(order);
    }

    public List<CustomerOrder> getAllOrderBySeller(Seller seller){
        List<SeatTable> tableList = seller.getTableList();
        List<CustomerOrder> list = new ArrayList<>();
        for (SeatTable table : tableList){
            List<CustomerOrder> orders = table.getOrders();
            for (CustomerOrder order : orders){
                list.add(order);
            }
        }
        return list;
    }


    @Transactional
    public void completeOrder(Long orderId){
        Optional<CustomerOrder> byId = orderRepository.findById(orderId);
        if (byId.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"order not found");
        }
        byId.get().complete();
    }
}
