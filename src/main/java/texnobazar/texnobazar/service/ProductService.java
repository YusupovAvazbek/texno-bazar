package texnobazar.texnobazar.service;

import texnobazar.texnobazar.dto.ApiResult;
import texnobazar.texnobazar.dto.ProductDto;
import texnobazar.texnobazar.entity.Product;

import java.util.List;

public interface ProductService {
    ApiResult<Void> create(ProductDto dto);
    ApiResult<Void> delete(Long id);
    ApiResult<ProductDto> update(Long id,ProductDto dto);
    ApiResult<ProductDto> get(Long id);

    ApiResult<List<Product>> getAllProducts();
}
