package chanho.remoteordersystem.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class SeatTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tableId;
    private Long sellerId;
    private String tableName;

    public SeatTable(Long sellerId, String tableName) {
        this.sellerId = sellerId;
        this.tableName = tableName;
    }
}
