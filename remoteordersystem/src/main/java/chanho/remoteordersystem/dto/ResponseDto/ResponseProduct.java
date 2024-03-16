package chanho.remoteordersystem.dto.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseProduct {
    private Long productId;
    private String productName;
    private Long price;
}
