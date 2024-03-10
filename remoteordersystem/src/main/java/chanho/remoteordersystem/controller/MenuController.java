package chanho.remoteordersystem.controller;

import chanho.remoteordersystem.Service.ProductService;
import chanho.remoteordersystem.Service.SellerService;
import chanho.remoteordersystem.domain.Product;
import chanho.remoteordersystem.domain.Seller;
import chanho.remoteordersystem.dto.ProductCreateForm;
import chanho.remoteordersystem.dto.ProductDeleteForm;
import chanho.remoteordersystem.dto.ProductUpdateForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MenuController {
    private final ProductService productService;
    private final SellerService sellerService;

    @GetMapping("/menu_management")
    public String menuManagement(Principal principal, Model model){
        Seller sellerByEmail = sellerService.getSellerByEmail(principal.getName());
        List<Product> sellerAllProducts = productService.getSellerAllProducts(sellerByEmail.getSellerId());
        model.addAttribute("menus",sellerAllProducts);
        return "manage_menu";
    }

    @ResponseBody
    @PostMapping("/createmenu")
    public Product createMenu(Principal principal, @RequestBody ProductCreateForm productCreateForm){
        Seller sellerByEmail = sellerService.getSellerByEmail(principal.getName());
        log.info(productCreateForm.toString());
        Product product = new Product(productCreateForm.getProductName(), productCreateForm.getPrice(), sellerByEmail.getSellerId());
        productService.createMenu(product);
        return product;
    }

    @ResponseBody
    @PostMapping("/updatemenu")
    public Product updateMenu(Principal principal, @RequestBody ProductUpdateForm productUpdateForm){
        Product productById = productService.getProductById(productUpdateForm.getProductId());
        productById.updateProduct(productUpdateForm.getProductName(),productUpdateForm.getPrice());
        productService.updateMenu(productById);
        return productById;
    }

    @ResponseBody
    @PostMapping("/deletemenu")
    public ProductDeleteForm deleteMenu(Principal principal, @RequestBody ProductDeleteForm productDeleteForm){
        productService.deleteMenu(productDeleteForm.getProductId());
        return productDeleteForm;
    }
}
