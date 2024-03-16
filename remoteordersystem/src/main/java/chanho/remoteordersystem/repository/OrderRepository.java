package chanho.remoteordersystem.repository;

import chanho.remoteordersystem.domain.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<CustomerOrder,Long> {
}
