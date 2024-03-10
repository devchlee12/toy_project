package chanho.remoteordersystem.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class CustomerOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private Long productId;
    private LocalDateTime orderDate;
    private Long orderTable;
    private Long sellerId;
    private Boolean served;

    public CustomerOrder(Long productId, Long orderTable, Long sellerId) {
        this.productId = productId;
        this.orderDate = LocalDateTime.now();
        this.orderTable = orderTable;
        this.sellerId = sellerId;
        this.served = false;
    }

    public void complete(){
        this.served = true;
    }
}
