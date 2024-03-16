package chanho.remoteordersystem.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productName;
    private Long price;
    @ManyToOne
    private Seller seller;

    public void updateProduct(String productName,Long price){
        this.productName = productName;
        this.price = price;
    }

    public Product(String productName, Long price, Seller seller) {
        this.productName = productName;
        this.price = price;
        this.seller = seller;
    }
}
