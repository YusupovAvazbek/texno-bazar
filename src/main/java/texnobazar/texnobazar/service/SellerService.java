package texnobazar.texnobazar.service;

import texnobazar.texnobazar.dto.ApiResult;
import texnobazar.texnobazar.dto.ProductDto;
import texnobazar.texnobazar.dto.SellerDto;
import texnobazar.texnobazar.entity.Product;
import texnobazar.texnobazar.entity.Seller;

import java.util.List;

public interface SellerService {
    ApiResult<SellerDto> create(SellerDto dto);
    ApiResult<Void> delete(Long id);
    ApiResult<SellerDto> update(Long id,SellerDto dto);
    ApiResult<SellerDto> get(Long id);
    ApiResult<List<Seller>> getAllSellers();
}
