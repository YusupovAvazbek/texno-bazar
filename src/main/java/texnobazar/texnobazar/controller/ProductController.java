package texnobazar.texnobazar.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import texnobazar.texnobazar.dto.ApiResult;
import texnobazar.texnobazar.dto.ProductDto;
import texnobazar.texnobazar.service.ProductService;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult<Void> addProduct(@RequestBody ProductDto productDto){
        return productService.create(productDto);
    }
    @PatchMapping("/update")
    public ApiResult<ProductDto> updateProduct(@RequestBody ProductDto productDto){
        return productService.update(productDto);
    }
    @GetMapping("/{id}")
    public ApiResult<ProductDto> getById(@PathVariable Long id){
        return productService.get(id);
    }
    @DeleteMapping("/delete/{id}")
    public ApiResult<Void> delete(@PathVariable Long id){
        return productService.delete(id);
    }
}
