package texnobazar.texnobazar.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import texnobazar.texnobazar.dto.ApiResult;
import texnobazar.texnobazar.dto.SoldProductDto;
import texnobazar.texnobazar.service.ProductService;
import texnobazar.texnobazar.service.SoldProductService;

@Controller
@RequiredArgsConstructor
public class SoldProductController {
    private final SoldProductService soldProductService;
    private final ProductService productService;


    @GetMapping("/api/sell")
    public String getAllProducts(@RequestParam String period, Model model){
        model.addAttribute("sells",soldProductService.getAllSoldProducts(period).getData());
        return "sell/getAllSellProducts";
    }
    @PostMapping("/api/sell")
    public String addSell(@ModelAttribute("soldProduct") SoldProductDto soldProductDto){
        soldProductService.create(soldProductDto);
        return "redirect:/api/sell";
    }
    @GetMapping("/api/sell/new")
    public String addSellForm(Model model){
        SoldProductDto soldProductDto = new SoldProductDto();
        model.addAttribute("products",productService.getAllProducts().getData());
        model.addAttribute("soldProduct",soldProductDto);
        return "sell/addSoldProduct";
    }
    @PostMapping("/api/sell/{id}")
    public String updateProduct(@PathVariable Long id,@ModelAttribute("soldProduct") SoldProductDto soldProductDto){
        soldProductService.update(id,soldProductDto);
        return "redirect:/api/sell";
    }
    @GetMapping("/api/sell/update/{id}")
    public String update(@PathVariable Long id, Model model){
        model.addAttribute("products",productService.getAllProducts().getData());
        model.addAttribute("soldProduct",soldProductService.get(id).getData());
        return "sell/updateSoldProduct";
    }
    @GetMapping("/api/sell/get/{id}")
    public String getById(@PathVariable Long id, Model model){
        ApiResult<SoldProductDto> productDtoApiResult = soldProductService.get(id);
        model.addAttribute("sell",productDtoApiResult.getData());
        return "/sell/getProduct";
    }
    @GetMapping("/api/sell/delete/{id}")
    public String delete(@PathVariable Long id){
        soldProductService.delete(id);
        return "redirect:/api/sell";
    }
}
