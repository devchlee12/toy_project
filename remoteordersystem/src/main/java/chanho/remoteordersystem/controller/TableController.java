package chanho.remoteordersystem.controller;

import chanho.remoteordersystem.Service.SellerService;
import chanho.remoteordersystem.Service.TableService;
import chanho.remoteordersystem.domain.SeatTable;
import chanho.remoteordersystem.domain.Seller;
import chanho.remoteordersystem.dto.ResponseDto.ResponseTable;
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
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class TableController {
    private final TableService tableService;
    private final SellerService sellerService;

    @ResponseBody
    @PostMapping("/createtable")
    public ResponseTable createTable(@RequestBody TableCreateForm tableCreateForm, Principal principal){
        Seller seller = sellerService.getSellerByEmail(principal.getName());
        SeatTable seatTable = new SeatTable(seller, tableCreateForm.getTableNumber());
        tableService.create(seatTable);
        return new ResponseTable(seatTable.getId(), seatTable.getTableName());
    }

    @GetMapping("/tablemanagement")
    public String tableManagement(Principal principal, Model model){
        Seller seller = sellerService.getSellerByEmail(principal.getName());
        List<SeatTable> tables = tableService.getAllTableBySeller(seller.getId());
        List<ResponseTable> collect = tables.stream()
                .map(table -> new ResponseTable(table.getId(), table.getTableName()))
                .collect(Collectors.toList());
        model.addAttribute("tables" ,collect);
        return "manage_table";
    }
}
