package chanho.remoteordersystem.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sellerId;
    private String email;
    private String sellerName;
    private String password;

    public Seller(String email, String sellerName,String password) {
        this.email = email;
        this.sellerName = sellerName;
        this.password = password;
    }
}
