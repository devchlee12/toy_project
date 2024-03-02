package webproject.libraryreservationsystem.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import webproject.libraryreservationsystem.domain.Reserved;
import webproject.libraryreservationsystem.domain.SiteUser;
import webproject.libraryreservationsystem.domain.UserCreateForm;
import webproject.libraryreservationsystem.domain.UserLoginForm;
import webproject.libraryreservationsystem.service.ReserveService;
import webproject.libraryreservationsystem.service.UserService;

import java.security.Principal;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping
public class UserController {
    private final UserService userService;
    private final ReserveService reserveService;

    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm){
        return "signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup_form";
        }
        if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "signup_form";
        }
        try {
            userService.create(userCreateForm.getUserName(),
                    userCreateForm.getEmail(), userCreateForm.getPassword1());
        }
        catch(DataIntegrityViolationException e){
            bindingResult.reject("signupFailed","이미 등록된 사용자입니다.");
            return "signup_form";
        }
        catch(Exception e) {
            bindingResult.reject("signupFailed", e.getMessage());
            return "signup_form";
        }
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "login_form";
    }

    @GetMapping("/main")
    public String main(Model model, Principal principal){
        Optional<Reserved> usersReserved = reserveService.getUsersReserved(principal.getName());
        if (!usersReserved.isEmpty()){
            model.addAttribute("seat",usersReserved.get().getNum().toString());
        }
        return "main_page";
    }

    @GetMapping("/")
    public String root(){
        return "redirect:/main";
    }
}
