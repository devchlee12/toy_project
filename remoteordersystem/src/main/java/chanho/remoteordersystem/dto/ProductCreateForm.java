package chanho.remoteordersystem.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductCreateForm {
    private String productName;
    private Long price;
}
