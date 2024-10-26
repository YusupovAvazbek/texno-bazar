package texnobazar.texnobazar.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import texnobazar.texnobazar.dto.ProductDto;
import texnobazar.texnobazar.dto.SoldProductDto;
import texnobazar.texnobazar.entity.Product;
import texnobazar.texnobazar.entity.SoldProduct;

@Mapper(componentModel = "spring")
public abstract class SoldProductMapper implements BaseMapper<SoldProduct, SoldProductDto> {
    @Autowired
    protected SellerMapper sellerMapper;
    @Autowired
    protected ProductMapper productMapper;

    @Mapping(target = "soldProduct", expression = "java(productMapper.toEntity(soldProductDto.getSoldProduct()))")
    @Mapping(target = "soldBy", expression = "java(sellerMapper.toEntity(soldProductDto.getSoldBy()))")
    public abstract SoldProduct toEntity(SoldProductDto soldProductDto);

    @Mapping(target = "soldProduct", expression = "java(productMapper.toDto(soldProduct.getSoldProduct()))")
    @Mapping(target = "soldBy", expression = "java(sellerMapper.toDto(soldProduct.getSoldBy()))")
    public abstract SoldProductDto toDto(SoldProduct soldProduct);
}
