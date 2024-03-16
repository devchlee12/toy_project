package chanho.remoteordersystem.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class CustomerOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;
    private LocalDateTime orderDate;
    @ManyToOne(fetch = FetchType.LAZY)
    private SeatTable seatTable;
    private Boolean served;

    public CustomerOrder(Product product, SeatTable seatTable) {
        this.product = product;
        this.orderDate = LocalDateTime.now();
        this.seatTable = seatTable;
        this.served = false;
    }

    public void complete(){
        this.served = true;
    }
}
