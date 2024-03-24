package chanho.remoteordersystem.controller;

import chanho.remoteordersystem.Service.SellerService;
import chanho.remoteordersystem.domain.Seller;
import chanho.remoteordersystem.dto.SellerCreateForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class SellerController {
    private final SellerService sellerService;
    @GetMapping("/signup")
    public String signUp(SellerCreateForm sellerCreateForm){
        return "signup";
    }
    @PostMapping("/signup")
    public String signUp(SellerCreateForm sellerCreateForm, BindingResult bindingResult) {
        if (!sellerCreateForm.getPassword().equals(sellerCreateForm.getPasswordConfirm())){
            bindingResult.rejectValue("passwordConfirm","not_same");
        }
        if (sellerService.haveSeller(sellerCreateForm.getEmail())){
            bindingResult.rejectValue("email","duplicated");
        }
        if (bindingResult.hasErrors()){
            return "signup";
        }
        sellerService.create(sellerCreateForm.getEmail(),sellerCreateForm.getUsername(),sellerCreateForm.getPassword());
        return "redirect:/";
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }

}
