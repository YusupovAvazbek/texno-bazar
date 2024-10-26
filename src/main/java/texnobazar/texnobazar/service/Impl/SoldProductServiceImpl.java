package texnobazar.texnobazar.service.Impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import texnobazar.texnobazar.dto.ApiResult;
import texnobazar.texnobazar.dto.ErrorDto;
import texnobazar.texnobazar.dto.ProductDto;
import texnobazar.texnobazar.dto.SoldProductDto;
import texnobazar.texnobazar.entity.Product;
import texnobazar.texnobazar.entity.Seller;
import texnobazar.texnobazar.entity.SoldProduct;
import texnobazar.texnobazar.mapper.SoldProductMapper;
import texnobazar.texnobazar.repository.CustomRepositoryImpl;
import texnobazar.texnobazar.repository.ProductRepository;
import texnobazar.texnobazar.repository.SoldProductRepository;
import texnobazar.texnobazar.service.ProductService;
import texnobazar.texnobazar.service.SellerService;
import texnobazar.texnobazar.service.SoldProductService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static texnobazar.texnobazar.service.message.AppStatusCodes.*;
import static texnobazar.texnobazar.service.message.AppStatusMessages.*;
import static texnobazar.texnobazar.service.message.AppStatusMessages.DATABASE_ERROR;
@Service
@RequiredArgsConstructor
public class SoldProductServiceImpl implements SoldProductService {
    private final ProductRepository productRepository;
    private final SoldProductRepository soldProductRepository;
    private final CustomRepositoryImpl customRepository;
    private final SoldProductMapper soldProductMapper;
    private final SellerService sellerService;
    @Override
    public ApiResult<SoldProductDto> create(SoldProductDto dto) {
        try {
            dto.setSoldBy(sellerService.get(4L).getData());
            Optional<Product> byId = productRepository.findById(dto.getSoldProduct().getId());
            Integer count = byId.get().getCount();
            if( count > 0 && count > dto.getCount()) {
                Product product = byId.get();
                product.setCount(count - dto.getCount());
                SoldProduct soldProduct = soldProductMapper.toEntity(dto);
                soldProduct.setTimeSold(LocalDateTime.now());
                SoldProduct save = soldProductRepository.save(soldProduct);
                productRepository.save(product);


                return ApiResult.<SoldProductDto>builder()
                        .code(OK_CODE)
                        .message(OK)
                        .data(soldProductMapper.toDto(save))
                        .success(true)
                        .build();
            }
            return ApiResult.<SoldProductDto>builder()
                    .success(false)
                    .build();
        }catch (Exception e){
            e.printStackTrace();
            return ApiResult.<SoldProductDto>builder()
                    .success(false)
                    .message(DATABASE_ERROR+": "+e.getMessage())
                    .code(DATABASE_ERROR_CODE)
                    .errors(Collections.singleton(ErrorDto.builder()
                            .error(e.getMessage())
                            .build()))
                    .build();
        }
    }

    @Override
    public ApiResult<Void> delete(Long id) {
        if (id == null) {
            return ApiResult.<Void>builder()
                    .code(NULL_VALUE_CODE)
                    .message(NULL_VALUE)
                    .success(false)
                    .errors(Collections.singleton(ErrorDto.builder()
                            .error("Invalid input. is null.")
                            .field("id")
                            .build()))
                    .build();
        }
        try {
            Optional<SoldProduct> byId = soldProductRepository.findById(id);
            if(byId.isEmpty()){
                return ApiResult.<Void>builder()
                        .code(NOT_FOUND_ERROR_CODE)
                        .message(NOT_FOUND)
                        .success(false)
                        .errors(Collections.singleton(ErrorDto.builder()
                                .error("Product not found with ID: " + id)
                                .build()))
                        .build();
            }
            SoldProduct soldProduct = byId.get();
            soldProductRepository.delete(soldProduct);

            return ApiResult.<Void>builder()
                    .code(OK_CODE)
                    .message(OK)
                    .success(true)
                    .build();
        }catch (Exception e){
            return ApiResult.<Void>builder()
                    .success(false)
                    .message(DATABASE_ERROR+": "+e.getMessage())
                    .code(DATABASE_ERROR_CODE)
                    .errors(Collections.singleton(ErrorDto.builder()
                            .error(e.getMessage())
                            .build()))
                    .build();
        }
    }

    @Override
    public ApiResult<SoldProductDto> update(Long id,SoldProductDto dto) {
        if (dto == null || dto.getId() == null) {
            return ApiResult.<SoldProductDto>builder()
                    .code(NULL_VALUE_CODE)
                    .message(NULL_VALUE)
                    .success(false)
                    .errors(Collections.singleton(ErrorDto.builder()
                            .error("Invalid input. is null.")
                            .field("id")
                            .build()))
                    .build();
        }
        try {
            Optional<SoldProduct> byId = soldProductRepository.findById(id);
            if(byId.isEmpty()){
                return ApiResult.<SoldProductDto>builder()
                        .code(NOT_FOUND_ERROR_CODE)
                        .message(NOT_FOUND)
                        .success(false)
                        .errors(Collections.singleton(ErrorDto.builder()
                                .error("Product not found with ID: " + dto.getId())
                                .build()))
                        .build();
            }
            SoldProduct soldProduct = byId.get();
            if(dto.getCount() != null){
                soldProduct.setCount(dto.getCount());
                Optional<Product> byId1 = productRepository.findById(dto.getSoldProduct().getId());
                Product product = byId1.get();
                product.setCount(product.getCount() + soldProduct.getCount() - dto.getCount());
                productRepository.save(product);
            }if(dto.getHowMuchSold() != null){
                soldProduct.setHowMuchSold(dto.getHowMuchSold());
            }if(dto.getExpense()!= null){
                soldProduct.setExpense(dto.getExpense());
            }
            SoldProduct save = soldProductRepository.save(soldProduct);
            return ApiResult.<SoldProductDto>builder()
                    .code(OK_CODE)
                    .message(OK)
                    .data(soldProductMapper.toDto(save))
                    .success(true)
                    .build();


        }catch (Exception e){
            return ApiResult.<SoldProductDto>builder()
                    .success(false)
                    .message(DATABASE_ERROR+": "+e.getMessage())
                    .code(DATABASE_ERROR_CODE)
                    .errors(Collections.singleton(ErrorDto.builder()
                            .error(e.getMessage())
                            .build()))
                    .build();
        }
    }

    @Override
    public ApiResult<SoldProductDto> get(Long id) {
        if (id == null) {
            return ApiResult.<SoldProductDto>builder()
                    .code(NULL_VALUE_CODE)
                    .message(NULL_VALUE)
                    .success(false)
                    .errors(Collections.singleton(ErrorDto.builder()
                            .error("Invalid input. is null.")
                            .field("id")
                            .build()))
                    .build();
        }
        try {
            return soldProductRepository.findById(id)
                    .map(u -> ApiResult.<SoldProductDto>builder()
                            .code(OK_CODE)
                            .message(OK)
                            .data(soldProductMapper.toDto(u))
                            .success(true)
                            .build())
                    .orElse(ApiResult.<SoldProductDto>builder()
                            .code(HttpStatus.NOT_FOUND.value())
                            .message("Product not found with ID: " + id)
                            .success(false)
                            .errors(Collections.singleton(ErrorDto.builder()
                                    .error("Product not found with ID: " + id)
                                    .build()))
                            .build());
        }catch (Exception e){
            return ApiResult.<SoldProductDto>builder()
                    .success(false)
                    .message(DATABASE_ERROR+": "+e.getMessage())
                    .code(DATABASE_ERROR_CODE)
                    .errors(Collections.singleton(ErrorDto.builder()
                            .error(e.getMessage())
                            .build()))
                    .build();
        }

    }

    @Override
    public ApiResult<Page<SoldProductDto>> getAll(Integer page , Integer size) {
        try {
            Long count = soldProductRepository.count();
            PageRequest pageRequest = PageRequest.of(
                    (count / size) <= page ?
                            (count % size == 0 ? (int) (count / size) - 1
                                    : (int) (count / size))
                            : page,
                    size);
            Page<SoldProductDto> soldProductDtos = soldProductRepository.findAll(pageRequest)
                    .map(soldProductMapper::toDto);

            return ApiResult.<Page<SoldProductDto>>builder()
                    .data(soldProductDtos)
                    .code(OK_CODE)
                    .message(OK)
                    .success(true)
                    .build();
        }catch (Exception e){
            return ApiResult.<Page<SoldProductDto>>builder()
                    .success(false)
                    .message(DATABASE_ERROR+": "+e.getMessage())
                    .code(DATABASE_ERROR_CODE)
                    .errors(Collections.singleton(ErrorDto.builder()
                            .error(e.getMessage())
                            .build()))
                    .build();
        }
    }

    @Override
    public ApiResult<List<SoldProduct>> getAllSoldProducts(String query) {
        try {
            List<SoldProduct> all;
            if(query.equals("ALL")|| query == null) {
                all = soldProductRepository.getAllSoldProducts();
            }else {
                all = customRepository.getSoldProduct(query);
            }
            return ApiResult.<List<SoldProduct>>builder()
                    .code(OK_CODE)
                    .message(OK)
                    .success(true)
                    .data(all)
                    .build();
        }catch (Exception e){
            return ApiResult.<List<SoldProduct>>builder()
                    .success(false)
                    .message(DATABASE_ERROR+": "+e.getMessage())
                    .code(DATABASE_ERROR_CODE)
                    .errors(Collections.singleton(ErrorDto.builder()
                            .error(e.getMessage())
                            .build()))
                    .build();
        }
    }


}
