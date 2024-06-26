package chanho.remoteordersystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class QueueDto {
    String productName;
    String tableName;
    LocalDateTime orderTime;
}
