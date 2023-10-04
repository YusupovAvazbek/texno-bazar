package texnobazar.texnobazar.service.Impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import texnobazar.texnobazar.dto.ApiResult;
import texnobazar.texnobazar.dto.ErrorDto;
import texnobazar.texnobazar.dto.ProductDto;
import texnobazar.texnobazar.entity.Product;
import texnobazar.texnobazar.mapper.CategoryMapper;
import texnobazar.texnobazar.mapper.ProductMapper;
import texnobazar.texnobazar.repository.ProductRepository;
import texnobazar.texnobazar.service.ProductService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static texnobazar.texnobazar.service.message.AppStatusCodes.*;
import static texnobazar.texnobazar.service.message.AppStatusMessages.*;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final CategoryMapper categoryMapper;
    @Override
    public ApiResult<Void> create(ProductDto dto) {
        try {
            Optional<Product> productByNameAndPrice = productRepository.getProductByNameAndPriceUSD(dto.getName(), dto.getPriceUSD());
            double uzsPrice = convertUSDToUZS(dto.getPriceUSD());
            if(productByNameAndPrice.isPresent()){
                Product product = productByNameAndPrice.get();
                if (uzsPrice != -1) {
                    product.setPriceUZS(uzsPrice);
                }
                product.setCount(product.getCount()+dto.getCount());
                productRepository.save(product);
                return ApiResult.<Void>builder()
                        .code(OK_CODE)
                        .message(OK)
                        .success(true)
                        .build();
            }else{
                Product product = productMapper.toEntity(dto);
                if(uzsPrice != -1){
                    product.setPriceUZS(uzsPrice);
                }
                product.setCreatedTime(LocalDateTime.now());
                productRepository.save(product);
                return ApiResult.<Void>builder()
                        .code(OK_CODE)
                        .message(OK)
                        .success(true)
                        .build();
            }
        }catch (Exception e){
            return ApiResult.<Void>builder()
                    .success(false)
                    .message(DATABASE_ERROR+": "+e.getMessage())
                    .code(DATABASE_ERROR_CODE)
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
        Optional<Product> byId = productRepository.findById(id);
        if (!byId.isPresent()) {
            return ApiResult.<Void>builder()
                    .code(NOT_FOUND_ERROR_CODE)
                    .message(NOT_FOUND)
                    .success(false)
                    .errors(Collections.singleton(ErrorDto.builder()
                            .error("Product not found with ID: " + id)
                            .build()))
                    .build();
        }
        productRepository.delete(byId.get());
        return ApiResult.<Void>builder()
                .code(OK_CODE)
                .message(OK)
                .success(true)
                .build();
    }

    @Override
    public ApiResult<ProductDto> update(ProductDto dto) {
        if (dto == null || dto.getId() == null) {
            return ApiResult.<ProductDto>builder()
                    .code(NULL_VALUE_CODE)
                    .message(NULL_VALUE)
                    .success(false)
                    .errors(Collections.singleton(ErrorDto.builder()
                            .error("Invalid input. ProductDto or ID is null.")
                            .field("id")
                            .build()))
                    .build();
        }

        Optional<Product> existingProduct = productRepository.findById(dto.getId());

        if (!existingProduct.isPresent()) {
            return ApiResult.<ProductDto>builder()
                    .code(NOT_FOUND_ERROR_CODE)
                    .message(NOT_FOUND)
                    .success(false)
                    .errors(Collections.singleton(ErrorDto.builder()
                            .error("Product not found with ID: " + dto.getId())
                            .build()))
                    .build();
        }

        Product product = existingProduct.get();
        try {
            if(dto.getName() != null){
                product.setName(dto.getName());
            }if(dto.getCategory() != null){
                product.setCategory(categoryMapper.toEntity(dto.getCategory()));
            }if(dto.getPriceUSD() != null){
                product.setPriceUSD(dto.getPriceUSD());
            }
            Product save = productRepository.save(product);
            return ApiResult.<ProductDto>builder()
                    .code(OK_CODE)
                    .message(OK)
                    .success(true)
                    .data(productMapper.toDto(save))
                    .build();
        }catch (Exception e){
            return ApiResult.<ProductDto>builder()
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
    public ApiResult<ProductDto> get(Long id) {
        if (id == null) {
            return ApiResult.<ProductDto>builder()
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
            return productRepository.findById(id)
                    .map(u -> ApiResult.<ProductDto>builder()
                            .code(OK_CODE)
                            .message(OK)
                            .data(productMapper.toDto(u))
                            .success(true)
                            .build())
                    .orElse(ApiResult.<ProductDto>builder()
                            .code(HttpStatus.NOT_FOUND.value())
                            .message("Product not found with ID: " + id)
                            .success(false)
                            .errors(Collections.singleton(ErrorDto.builder()
                                    .error("Product not found with ID: " + id)
                                    .build()))
                            .build());
        }catch (Exception e){
            return ApiResult.<ProductDto>builder()
                    .success(false)
                    .message(DATABASE_ERROR+": "+e.getMessage())
                    .code(DATABASE_ERROR_CODE)
                    .errors(Collections.singleton(ErrorDto.builder()
                            .error(e.getMessage())
                            .build()))
                    .build();
        }
    }

    public static double convertUSDToUZS(Double USD) {
        try {
            URL url = new URL("https://cbu.uz/uz/arkhiv-kursov-valyut/json/");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode currencyRates = objectMapper.readTree(response.toString());
            for (JsonNode currency : currencyRates) {
                if ("USD".equals(currency.get("Ccy").asText())) {
                    double rate = currency.get("Rate").asDouble();

                    double uzsAmount = USD * rate;

                    return uzsAmount;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }
}
