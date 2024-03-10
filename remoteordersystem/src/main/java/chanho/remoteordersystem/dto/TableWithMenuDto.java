package chanho.remoteordersystem.dto;

import chanho.remoteordersystem.domain.CustomerOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class TableWithMenuDto {
    String tableName;
    List<OrderAlarmDto> orders;
}
