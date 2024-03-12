package chanho.remoteordersystem.controller;

import chanho.remoteordersystem.Service.SellerService;
import chanho.remoteordersystem.dto.SellerCreateForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class SellerController {
    private final SellerService sellerService;
    @GetMapping("/signup")
    public String signUp(){
        return "signup";
    }
    @PostMapping("/signup")
    public String signUp(SellerCreateForm sellerCreateForm) {
        if (!sellerCreateForm.getPassword().equals(sellerCreateForm.getPasswordConfirm())){
            //비밀번호 오류 처리
        }
        sellerService.create(sellerCreateForm.getEmail(),sellerCreateForm.getUsername(),sellerCreateForm.getPassword());
        return "redirect:/";
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }

}
