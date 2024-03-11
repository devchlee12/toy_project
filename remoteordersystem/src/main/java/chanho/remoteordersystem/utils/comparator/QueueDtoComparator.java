package chanho.remoteordersystem.utils.comparator;

import chanho.remoteordersystem.dto.QueueDto;

import java.util.Comparator;

public class QueueDtoComparator implements Comparator<QueueDto> {

    @Override
    public int compare(QueueDto o1, QueueDto o2) {
        if (o1.getOrderTime().isAfter(o2.getOrderTime()))
            return 1;
        else
            return -1;
    }
}
