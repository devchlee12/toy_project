package chanho.remoteordersystem.repository;

import chanho.remoteordersystem.domain.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<CustomerOrder,Long> {
    public List<CustomerOrder> findAllBySellerId(Long sellerId);
    public List<CustomerOrder> findAllByOrderTable(Long tableId);
    public List<CustomerOrder> findAllByOrderTableAndServedFalse(Long tableId);
    public List<CustomerOrder> findAllByServedFalse();
}
