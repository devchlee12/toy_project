package chanho.remoteordersystem.Service;

import chanho.remoteordersystem.domain.Product;
import chanho.remoteordersystem.dto.ProductUpdateForm;
import chanho.remoteordersystem.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional
    public Product createMenu(Product product){
        productRepository.save((product));
        return product;
    }

    @Transactional
    public void updateMenu(Product product, ProductUpdateForm productUpdateForm){
//        Optional<Product> updatingProduct = productRepository.findById(product.getProductId());
//        if (updatingProduct.isEmpty()){
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"product not found");
//        }
        product.updateProduct(productUpdateForm.getProductName(),productUpdateForm.getPrice());
    }

    @Transactional
    public void deleteMenu(Long id){
        productRepository.deleteById(id);
    }

    public List<Product> getSellerAllProducts(Long sellerId){
        return productRepository.findAllBySellerId(sellerId);
    }

    public Product getProductById(Long productId){
        Optional<Product> byId = productRepository.findById(productId);
        if (byId.isEmpty()){
            log.info("Exception : getProductById");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "product with the id is not found");
        }
        return byId.get();
    }
}