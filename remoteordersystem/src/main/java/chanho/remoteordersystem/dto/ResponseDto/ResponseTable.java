package chanho.remoteordersystem.dto.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseTable {
    private Long tableId;
    private String tableName;
}
