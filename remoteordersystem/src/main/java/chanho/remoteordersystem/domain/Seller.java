package chanho.remoteordersystem.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String sellerName;
    private String password;
    @OneToMany(mappedBy = "seller")
    List<Product> productList = new ArrayList<>();

    @OneToMany(mappedBy = "seller")
    List<SeatTable> tableList = new ArrayList<>();

    public Seller(String email, String sellerName,String password) {
        this.email = email;
        this.sellerName = sellerName;
        this.password = password;
    }
}
