package texnobazar.texnobazar.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import texnobazar.texnobazar.dto.ApiResult;
import texnobazar.texnobazar.dto.ErrorDto;
import texnobazar.texnobazar.dto.SellerDto;
import texnobazar.texnobazar.entity.Seller;
import texnobazar.texnobazar.mapper.SellerMapper;
import texnobazar.texnobazar.repository.SellerRepository;
import texnobazar.texnobazar.service.SellerService;
import texnobazar.texnobazar.service.message.AppStatusCodes;

import java.util.Collections;
import java.util.Optional;

import static texnobazar.texnobazar.service.message.AppStatusCodes.*;
import static texnobazar.texnobazar.service.message.AppStatusMessages.*;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {
    private final SellerRepository sellerRepository;
    private final SellerMapper sellerMapper;
    @Override
    public ApiResult<SellerDto> create(SellerDto dto) {
        try {
            Optional<Seller> sellerByUsername = sellerRepository.getSellerByUsername(dto.getUsername());
            if(sellerByUsername.isEmpty()){
                return ApiResult.<SellerDto>builder()
                        .code(AppStatusCodes.ALREADY_EXISTS_ERROR_CODE)
                        .message(ALREADY_EXISTS)
                        .success(false)
                        .build();
            }
            Seller seller = sellerMapper.toEntity(dto);
            seller.setIsActive((short) 1);

            Seller save = sellerRepository.save(seller);

            return ApiResult.<SellerDto>builder()
                    .code(OK_CODE)
                    .message(OK)
                    .data(sellerMapper.toDto(save))
                    .success(true)
                    .build();
        }catch (Exception e){
            return ApiResult.<SellerDto>builder()
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
            Optional<Seller> sellerByIdAndIsActive = sellerRepository.getSellerByIdAndIsActive(id, (short) 1);
            if(sellerByIdAndIsActive.isEmpty()){
                return ApiResult.<Void>builder()
                        .code(NOT_FOUND_ERROR_CODE)
                        .message(NOT_FOUND)
                        .success(false)
                        .errors(Collections.singleton(ErrorDto.builder()
                                .error("Product not found with ID: " + id)
                                .build()))
                        .build();
            }
            Seller seller = sellerByIdAndIsActive.get();
            seller.setIsActive((short) 0);
            sellerRepository.save(seller);

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
    public ApiResult<SellerDto> update(SellerDto dto) {
        if (dto == null || dto.getId() == null) {
            return ApiResult.<SellerDto>builder()
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
            Optional<Seller> sellerByIdAndIsActive = sellerRepository.getSellerByIdAndIsActive(dto.getId(), (short) 1);
            if(sellerByIdAndIsActive.isEmpty()){
                return ApiResult.<SellerDto>builder()
                        .code(NOT_FOUND_ERROR_CODE)
                        .message(NOT_FOUND)
                        .success(false)
                        .errors(Collections.singleton(ErrorDto.builder()
                                .error("Product not found with ID: " + dto.getId())
                                .build()))
                        .build();
            }
            Seller seller = sellerByIdAndIsActive.get();
            if(dto.getUsername() != null){
                seller.setUsername(dto.getUsername());
            }if(dto.getPassword() != null){
                seller.setPassword(dto.getPassword());
            }if(dto.getFullName() != null){
                seller.setFullName(dto.getFullName());
            }
            Seller save = sellerRepository.save(seller);
            return ApiResult.<SellerDto>builder()
                    .code(OK_CODE)
                    .message(OK)
                    .data(sellerMapper.toDto(save))
                    .success(true)
                    .build();


        }catch (Exception e){
            return ApiResult.<SellerDto>builder()
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
    public ApiResult<SellerDto> get(Long id) {
        if (id == null) {
            return ApiResult.<SellerDto>builder()
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
            return sellerRepository.findById(id)
                    .map(u -> ApiResult.<SellerDto>builder()
                            .code(OK_CODE)
                            .message(OK)
                            .data(sellerMapper.toDto(u))
                            .success(true)
                            .build())
                    .orElse(ApiResult.<SellerDto>builder()
                            .code(HttpStatus.NOT_FOUND.value())
                            .message("Product not found with ID: " + id)
                            .success(false)
                            .errors(Collections.singleton(ErrorDto.builder()
                                    .error("Product not found with ID: " + id)
                                    .build()))
                            .build());
        }catch (Exception e){
            return ApiResult.<SellerDto>builder()
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
