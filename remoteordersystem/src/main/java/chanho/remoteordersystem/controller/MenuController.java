package chanho.remoteordersystem.controller;

import chanho.remoteordersystem.Service.ProductService;
import chanho.remoteordersystem.Service.SellerService;
import chanho.remoteordersystem.domain.Product;
import chanho.remoteordersystem.domain.Seller;
import chanho.remoteordersystem.dto.ProductCreateForm;
import chanho.remoteordersystem.dto.ProductDeleteForm;
import chanho.remoteordersystem.dto.ProductUpdateForm;
import chanho.remoteordersystem.dto.ResponseDto.ResponseProduct;
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
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MenuController {
    private final ProductService productService;
    private final SellerService sellerService;

    @GetMapping("/menu_management")
    public String menuManagement(Principal principal, Model model){
        Seller sellerByEmail = sellerService.getSellerByEmail(principal.getName());
        List<Product> sellerAllProducts = sellerByEmail.getProductList();
        List<ResponseProduct> list = sellerAllProducts.stream()
                .map(product -> new ResponseProduct(product.getId(), product.getProductName(), product.getPrice()))
                .collect(Collectors.toList());
        model.addAttribute("menus",list);
        return "manage_menu";
    }

    @ResponseBody
    @PostMapping("/createmenu")
    public ResponseProduct createMenu(Principal principal, @RequestBody ProductCreateForm productCreateForm){
        Seller sellerByEmail = sellerService.getSellerByEmail(principal.getName());
        Product product = new Product(productCreateForm.getProductName(), productCreateForm.getPrice(), sellerByEmail);
        productService.createMenu(product);
        return new ResponseProduct(product.getId(),product.getProductName(),product.getPrice());
    }

    @ResponseBody
    @PostMapping("/updatemenu")
    public ResponseProduct updateMenu(Principal principal, @RequestBody ProductUpdateForm productUpdateForm){
        Product productById = productService.getProductById(productUpdateForm.getProductId());
        productService.updateMenu(productById, productUpdateForm);
        return new ResponseProduct(productById.getId(), productById.getProductName(),productById.getPrice());
    }

    @ResponseBody
    @PostMapping("/deletemenu")
    public ProductDeleteForm deleteMenu(Principal principal, @RequestBody ProductDeleteForm productDeleteForm){
        productService.deleteMenu(productDeleteForm.getProductId());
        return productDeleteForm;
    }
}
