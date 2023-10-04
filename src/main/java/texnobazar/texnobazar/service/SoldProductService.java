package texnobazar.texnobazar.service;

import org.springframework.data.domain.Page;
import texnobazar.texnobazar.dto.ApiResult;
import texnobazar.texnobazar.dto.SellerDto;
import texnobazar.texnobazar.dto.SoldProductDto;

import java.util.ArrayList;
import java.util.Date;

public interface SoldProductService {
    ApiResult<SoldProductDto> create(SoldProductDto dto);
    ApiResult<Void> delete(Long id);
    ApiResult<SoldProductDto> update(SoldProductDto dto);
    ApiResult<SoldProductDto> get(Long id);
    ApiResult<Page<SoldProductDto>> getAll(Integer page , Integer size);
}
