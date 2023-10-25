package texnobazar.texnobazar.service.Impl;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import texnobazar.texnobazar.dto.*;
import texnobazar.texnobazar.entity.Category;
import texnobazar.texnobazar.entity.Product;
import texnobazar.texnobazar.mapper.CategoryMapper;
import texnobazar.texnobazar.repository.CategoryRepository;
import texnobazar.texnobazar.repository.ProductRepository;
import texnobazar.texnobazar.service.CategoryService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static texnobazar.texnobazar.service.message.AppStatusCodes.DATABASE_ERROR_CODE;
import static texnobazar.texnobazar.service.message.AppStatusCodes.OK_CODE;
import static texnobazar.texnobazar.service.message.AppStatusMessages.DATABASE_ERROR;
import static texnobazar.texnobazar.service.message.AppStatusMessages.OK;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final ProductRepository productRepository;


    @Override
    public Boolean create(CategoryDto dto) {
        try {
            categoryRepository.save(categoryMapper.toEntity(dto));
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public Boolean delete(Long id) {
        try {
            List<Product> allProducts = productRepository.getAllProducts();
            for (Product allProduct : allProducts) {
                if(allProduct.getCategory().getId() != id){
                    categoryRepository.deleteById(id);
                    return true;
                }else{
                    continue;
                }
            }
            return false;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public Boolean update(Long id,CategoryDto dto) {
        try {
            Optional<Category> byId = categoryRepository.findById(id);
            Category category = byId.get();
            if(dto.getName() != null){
                category.setName(dto.getName());
            }
            categoryRepository.save(category);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public ApiResult<CategoryDto> get(Long id) {
        try {
            return categoryRepository.findById(id)
                    .map(u -> ApiResult.<CategoryDto>builder()
                            .code(OK_CODE)
                            .message(OK)
                            .data(categoryMapper.toDto(u))
                            .success(true)
                            .build())
                    .orElse(ApiResult.<CategoryDto>builder()
                            .code(HttpStatus.NOT_FOUND.value())
                            .message("Category not found with ID: " + id)
                            .success(false)
                            .errors(Collections.singleton(ErrorDto.builder()
                                    .error("Category not found with ID: " + id)
                                    .build()))
                            .build());
        }catch (Exception e){
            return ApiResult.<CategoryDto>builder()
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
    public ApiResult<List<Product>> getProductByCategoryId(Long id) {
        try {
            List<Product> all = productRepository.getProductByCategory_Id(id);
            return ApiResult.<List<Product>>builder()
                    .code(OK_CODE)
                    .message(OK)
                    .success(true)
                    .data(all)
                    .build();
        }catch (Exception e){
            return ApiResult.<List<Product>>builder()
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
    public List<Category> getAllCategories() {
        Iterable<Category> all = categoryRepository.findAll();
        return (List<Category>) all;
    }
}
