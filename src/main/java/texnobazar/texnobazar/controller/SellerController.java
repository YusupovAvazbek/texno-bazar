package texnobazar.texnobazar.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import texnobazar.texnobazar.dto.ApiResult;
import texnobazar.texnobazar.dto.ProductDto;
import texnobazar.texnobazar.dto.SellerDto;
import texnobazar.texnobazar.service.SellerService;

@Controller
@RequiredArgsConstructor
public class SellerController {
    private final SellerService sellerService;
    @GetMapping("/api/seller")
    public String getAllSeller(Model model){
        model.addAttribute("sellers",sellerService.getAllSellers().getData());
        return "seller/getAllSellers";
    }
    @PostMapping("/api/seller")
    public String addSeller(@ModelAttribute("seller") SellerDto seller){
        sellerService.create(seller);
        return "redirect:/api/seller";
    }
    @GetMapping("/api/seller/new")
    public String addProductForm(Model model){
        SellerDto sellerDto = new SellerDto();
        model.addAttribute("seller",sellerDto);
        return "seller/addSeller";
    }
    @PostMapping("/api/seller/{id}")
    public String updateSeller(@PathVariable Long id,@ModelAttribute("seller") SellerDto sellerDto){
        sellerService.update(id,sellerDto);
        return "redirect:/api/seller";

    }
    @GetMapping("/api/seller/update/{id}")
    public String update(@PathVariable Long id, Model model){
        model.addAttribute("seller",sellerService.get(id).getData());
        return "seller/updateSeller";
    }
    @GetMapping("/api/seller/get/{id}")
    public ApiResult<SellerDto> getByIdSeller(@PathVariable Long id){
        return sellerService.get(id);
    }
    @GetMapping("/api/seller/delete/{id}")
    public String deleteSeller(@PathVariable Long id){
        sellerService.delete(id);
        return "redirect:/api/seller";
    }
}
