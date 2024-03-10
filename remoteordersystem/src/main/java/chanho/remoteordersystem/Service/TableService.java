package chanho.remoteordersystem.Service;

import chanho.remoteordersystem.domain.SeatTable;
import chanho.remoteordersystem.domain.Seller;
import chanho.remoteordersystem.repository.TableRepository;
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
public class TableService {
    private final TableRepository tableRepository;

    @Transactional
    public void create(SeatTable seatTable){
        tableRepository.save(seatTable);
    }

    public List<SeatTable> getAllTableBySeller(Long id){
        List<SeatTable> allTable = tableRepository.findAllBySellerId(id);
        return allTable;
    }

    public SeatTable getTableById(Long id){
        Optional<SeatTable> byId = tableRepository.findById(id);
        return byId.get();
    }

    public Long getSellerIdByTableId(Long id){
        Optional<SeatTable> byId = tableRepository.findById(id);
        if (byId.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"seller with the tableId not found");
        }
        return byId.get().getSellerId();
    }
}
