package chanho.remoteordersystem.repository;

import chanho.remoteordersystem.domain.SeatTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TableRepository extends JpaRepository<SeatTable,Long> {
    List<SeatTable> findAllBySellerId(Long id);
}
