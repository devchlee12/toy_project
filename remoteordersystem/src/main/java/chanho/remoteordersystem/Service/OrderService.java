package chanho.remoteordersystem.Service;

import chanho.remoteordersystem.domain.CustomerOrder;
import chanho.remoteordersystem.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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

    public List<CustomerOrder> getAllOrderBySeller(Long sellerId){
        return orderRepository.findAllBySellerId(sellerId);
    }

    public List<CustomerOrder> getAllOrderByTable(Long tableId){
        return orderRepository.findAllByOrderTable(tableId);
    }

    public List<CustomerOrder> getAllOrderByTableAndServed(Long tableId){
        return orderRepository.findAllByOrderTableAndServedFalse(tableId);
    }

    public List<CustomerOrder> getAllOrderNotServed(){
        return orderRepository.findAllByServedFalse();
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
