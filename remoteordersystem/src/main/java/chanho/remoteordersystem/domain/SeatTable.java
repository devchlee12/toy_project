package chanho.remoteordersystem.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class SeatTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Seller seller;
    @OneToMany(mappedBy = "seatTable", cascade = CascadeType.PERSIST)
    private List<CustomerOrder> orders = new ArrayList<>();
    private String tableName;

    public SeatTable(Seller seller, String tableName) {
        this.seller = seller;
        this.tableName = tableName;
    }
}
