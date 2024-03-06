package chanho.nekarainfo.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class LineInfoServiceTest {
    LineInfoService lineInfoService = new LineInfoService();

    @Test
    void getWell() throws IOException {
        lineInfoService.getInfo();
    }
}