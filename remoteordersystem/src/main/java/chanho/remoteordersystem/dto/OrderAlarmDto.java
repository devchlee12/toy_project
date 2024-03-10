package chanho.remoteordersystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderAlarmDto {
    String productName;
    Long orderId;
}
