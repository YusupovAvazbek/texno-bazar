package texnobazar.texnobazar.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import texnobazar.texnobazar.dto.ApiResult;
import texnobazar.texnobazar.dto.CategoryDto;
import texnobazar.texnobazar.dto.ProductDto;
import texnobazar.texnobazar.entity.Product;
import texnobazar.texnobazar.service.CategoryService;
import texnobazar.texnobazar.service.ProductService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    @GetMapping("/api/category")
    public String getAllCategories(Model model){
        model.addAttribute("categories",categoryService.getAllCategories());
        return "category/getAllCategories";
    }
    @PostMapping("/api/category")
    public String addCategory(@ModelAttribute("category")CategoryDto categoryDto){
        categoryService.create(categoryDto);
        return "redirect:/api/category";
    }
    @GetMapping("/api/category/new")
    public String addCategoryForm(Model model){
        CategoryDto categoryDto = new CategoryDto();
        model.addAttribute("category",categoryDto);
        return "category/addCategory";
    }

    @PostMapping("/api/category/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute("category") CategoryDto categoryDto){
        categoryService.update(id,categoryDto);
        return "redirect:/api/category";
    }
    @GetMapping("/api/category/update/{id}")
    public String update(@PathVariable Long id, Model model){
        model.addAttribute("category",categoryService.get(id).getData());
        return "category/updateCategory";
    }
    @GetMapping("/api/category/get/{id}")
    public String getById(@PathVariable Long id, Model model){
        ApiResult<List<Product>> productByCategoryId = categoryService.getProductByCategoryId(id);
        model.addAttribute("products",productByCategoryId.getData());
        return "product/getAllProducts";
    }
    @GetMapping("/api/category/delete/{id}")
    public String delete(@PathVariable Long id){
        categoryService.delete(id);
        return "redirect:/api/category";
    }
}
