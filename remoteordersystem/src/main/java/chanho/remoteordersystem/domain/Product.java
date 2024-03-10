package chanho.remoteordersystem.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String productName;
    private Long price;
    private Long sellerId;

    public void updateProduct(String productName,Long price){
        this.productName = productName;
        this.price = price;
    }

    public Product(String productName, Long price, Long sellerId) {
        this.productName = productName;
        this.price = price;
        this.sellerId = sellerId;
    }
}
