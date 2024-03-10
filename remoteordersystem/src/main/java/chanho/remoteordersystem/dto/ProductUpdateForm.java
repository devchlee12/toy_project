package chanho.remoteordersystem.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductUpdateForm {
    private Long productId;
    private String productName;
    private Long price;
}
