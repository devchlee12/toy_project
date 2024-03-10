package chanho.remoteordersystem.repository;

import chanho.remoteordersystem.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findAllBySellerId(Long sellerId);
}
