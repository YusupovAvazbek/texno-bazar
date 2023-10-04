package texnobazar.texnobazar.mapper;

import org.mapstruct.Mapper;
import texnobazar.texnobazar.dto.ProductDto;
import texnobazar.texnobazar.entity.Product;
import java.util.ArrayList;
@Mapper(componentModel = "spring")
public interface ProductMapper{
    ProductDto toDto(Product product);
    Product toEntity(ProductDto productDto);
    ArrayList<ProductDto> toDtoList(ArrayList<Product> products);
    ArrayList<Product> toEntityList(ArrayList<ProductDto> productDtos);
}
