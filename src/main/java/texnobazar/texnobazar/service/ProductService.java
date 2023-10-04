package texnobazar.texnobazar.service;

import texnobazar.texnobazar.dto.ApiResult;
import texnobazar.texnobazar.dto.ProductDto;

public interface ProductService {
    ApiResult<Void> create(ProductDto dto);
    ApiResult<Void> delete(Long id);
    ApiResult<ProductDto> update(ProductDto dto);
    ApiResult<ProductDto> get(Long id);
}
