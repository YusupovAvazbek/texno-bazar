package texnobazar.texnobazar.service;

import texnobazar.texnobazar.dto.ApiResult;
import texnobazar.texnobazar.dto.ProductDto;
import texnobazar.texnobazar.dto.SellerDto;

public interface SellerService {
    ApiResult<SellerDto> create(SellerDto dto);
    ApiResult<Void> delete(Long id);
    ApiResult<SellerDto> update(SellerDto dto);
    ApiResult<SellerDto> get(Long id);
}
