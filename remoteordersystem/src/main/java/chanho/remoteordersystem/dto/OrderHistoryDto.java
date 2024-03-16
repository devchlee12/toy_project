package chanho.remoteordersystem.dto;

import chanho.remoteordersystem.domain.CustomerOrder;
import chanho.remoteordersystem.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OrderHistoryDto {
    private String productName;
    private LocalDateTime orderDate;
    private Long orderTable;
    private Long price;

    public OrderHistoryDto(Product product, CustomerOrder order) {
        this.productName = product.getProductName();
        this.orderDate = order.getOrderDate();
        this.orderTable = product.getId();
        this.price = product.getPrice();
    }
}
