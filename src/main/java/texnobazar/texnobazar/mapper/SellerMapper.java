package texnobazar.texnobazar.mapper;

import org.mapstruct.Mapper;
import texnobazar.texnobazar.dto.ProductDto;
import texnobazar.texnobazar.dto.SellerDto;
import texnobazar.texnobazar.entity.Product;
import texnobazar.texnobazar.entity.Seller;

import java.util.ArrayList;

@Mapper(componentModel = "spring")
public interface SellerMapper{
    SellerDto toDto(Seller product);
    Seller toEntity(SellerDto productDto);
    ArrayList<SellerDto> toDtoList(ArrayList<Seller> sellers);
    ArrayList<Seller> toEntityList(ArrayList<SellerDto> sellerDtos);
}
