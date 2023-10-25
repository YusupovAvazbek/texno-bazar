package texnobazar.texnobazar.service;

import texnobazar.texnobazar.dto.ApiResult;
import texnobazar.texnobazar.dto.CategoryDto;
import texnobazar.texnobazar.dto.ProductDto;
import texnobazar.texnobazar.entity.Category;
import texnobazar.texnobazar.entity.Product;

import java.util.List;

public interface CategoryService {
    Boolean create(CategoryDto dto);
    Boolean delete(Long id);
    Boolean update(Long id,CategoryDto dto);
    ApiResult<CategoryDto> get(Long id);
    ApiResult<List<Product>> getProductByCategoryId(Long id);

    List<Category> getAllCategories();
}
