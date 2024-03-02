package webproject.libraryreservationsystem.controller;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import webproject.libraryreservationsystem.domain.Reserved;
import webproject.libraryreservationsystem.domain.SiteUser;
import webproject.libraryreservationsystem.service.ReserveService;
import webproject.libraryreservationsystem.service.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequestMapping
@RequiredArgsConstructor
@Controller
public class ReserveController {
    private final ReserveService reserveService;
    private final UserService userService;

    @ResponseBody
    @GetMapping("/reserved-seats-api")
    public List<Reserved> reservedList(Model model,Principal principal){
        return(reserveService.getReserveData());
    }
    @ResponseBody
    @PostMapping("/reservation")
    public String reservation(@RequestBody Reserved reserved, Principal principal){
        userService.initializeUid(reserved,principal);
        if (reserveService.hasSeated(reserved.getUid())) throw new RuntimeException();
        reserveService.saveReserveData(reserved);
        return "ok";
    }

    @Transactional
    @GetMapping("/cancelreservation")
    public String cancelReservation(Principal principal){
        reserveService.deleteUsersReserved(principal);
        return "redirect:/main";
    }
}
