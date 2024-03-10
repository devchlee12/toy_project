package chanho.remoteordersystem.controller;

import chanho.remoteordersystem.Service.SellerService;
import chanho.remoteordersystem.Service.TableService;
import chanho.remoteordersystem.domain.SeatTable;
import chanho.remoteordersystem.domain.Seller;
import chanho.remoteordersystem.dto.TableCreateForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TableController {
    private final TableService tableService;
    private final SellerService sellerService;

    @ResponseBody
    @PostMapping("/createtable")
    public SeatTable createTable(@RequestBody TableCreateForm tableCreateForm, Principal principal){
        Seller seller = sellerService.getSellerByEmail(principal.getName());
        SeatTable seatTable = new SeatTable(seller.getSellerId(), tableCreateForm.getTableNumber());
        tableService.create(seatTable);
        return seatTable;
    }

    @GetMapping("/tablemanagement")
    public String tableManagement(Principal principal, Model model){
        Seller seller = sellerService.getSellerByEmail(principal.getName());
        List<SeatTable> tables = tableService.getAllTableBySeller(seller.getSellerId());
        model.addAttribute("tables" ,tables);
        return "manage_table";
    }
}
