package chanho.remoteordersystem.mockdb;

import chanho.remoteordersystem.Service.OrderService;
import chanho.remoteordersystem.Service.ProductService;
import chanho.remoteordersystem.Service.SellerService;
import chanho.remoteordersystem.Service.TableService;
import chanho.remoteordersystem.domain.CustomerOrder;
import chanho.remoteordersystem.domain.Product;
import chanho.remoteordersystem.domain.SeatTable;
import chanho.remoteordersystem.repository.TableRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class mockdbTest {
    @Autowired
    public SellerService sellerService;
    @Autowired
    public TableService tableService;
    @Autowired
    public TableRepository tableRepository;
    @Autowired
    public OrderService orderService;
    @Autowired
    public ProductService productService;
    @Test
    public void createSeller(){
        String s = "test";
        String at = "@naver.com";
        for (int j = 1; j <= 1000;j++)
            sellerService.create(s + j + at,s + j,"1234");
    }

    @Test
    public void createTable() {
        String table = "table";
        for (int i = 1; i <= 100; i++) {
            for (int j = 1; j <= 20; j++) {
                tableService.create(new SeatTable(Long.valueOf(i),table + j));
            }
        }
    }

    @Test
    public void createProduct() {
        String table = "product";
        for (int i = 1; i <= 100; i++) {
            for (int j = 1; j <= 20; j++) {
                productService.createMenu(new Product(table + j,1000L,Long.valueOf(i)));
            }
        }
    }

    @Test
    public void createOrder(){
        List<SeatTable> all = tableRepository.findAll();
        for (SeatTable x : all) {
            List<Product> sellerAllProducts = productService.getSellerAllProducts(x.getSellerId());
            for (Product y : sellerAllProducts) {
                orderService.createOrder(new CustomerOrder(y.getProductId(),x.getTableId(),x.getSellerId()));
            }
        }
    }
}
