package chanho.remoteordersystem.dto;

import chanho.remoteordersystem.domain.CustomerOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OrderHistoryDto {
    private String productName;
    private Long orderId;
    private Long productId;
    private LocalDateTime orderDate;
    private Long orderTable;
    private Long sellerId;
    private Long price;
    private Boolean served;

    public OrderHistoryDto(String productName,Long price, CustomerOrder order) {
        this.productName = productName;
        this.orderId = order.getOrderId();
        this.productId = order.getProductId();
        this.orderDate = order.getOrderDate();
        this.orderTable = order.getOrderTable();
        this.sellerId = order.getSellerId();
        this.served = order.getServed();
        this.price = price;
    }
}
