package texnobazar.texnobazar.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import texnobazar.texnobazar.dto.ApiResult;
import texnobazar.texnobazar.dto.ProductDto;
import texnobazar.texnobazar.service.CategoryService;
import texnobazar.texnobazar.service.ProductService;


@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;
    @GetMapping("/api/product")
    public String getAllProducts(Model model){
        model.addAttribute("products",productService.getAllProducts().getData());
        return "product/getAllProducts";
    }
    @PostMapping("/api/product")
    public String addProduct(@ModelAttribute("product") ProductDto product){
        productService.create(product);
        return "redirect:/api/product";
    }
    @GetMapping("/api/product/new")
    public String addProductForm(Model model){
        ProductDto productDto = new ProductDto();
        model.addAttribute("categories",categoryService.getAllCategories());
        model.addAttribute("product",productDto);
        return "product/addProduct";
    }
    @PostMapping("/api/product/{id}")
    public String updateProduct(@PathVariable Long id,@ModelAttribute("product") ProductDto productDto){
        productService.update(id,productDto);
        return "redirect:/api/product";
    }
    @GetMapping("/api/product/update/{id}")
    public String update(@PathVariable Long id, Model model){
        model.addAttribute("categories",categoryService.getAllCategories());
        model.addAttribute("product",productService.get(id).getData());
        return "product/updateProduct";
    }
    @GetMapping("/api/product/get/{id}")
    public String getById(@PathVariable Long id, Model model){
        ApiResult<ProductDto> productDtoApiResult = productService.get(id);
        model.addAttribute("product",productDtoApiResult.getData());
        return "product/getProduct";
    }
    @GetMapping("/api/product/delete/{id}")
    public String delete(@PathVariable Long id){
       productService.delete(id);
       return "redirect:/api/product";
    }

}
