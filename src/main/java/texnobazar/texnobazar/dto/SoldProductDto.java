package texnobazar.texnobazar.dto;

import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import texnobazar.texnobazar.entity.Product;
import texnobazar.texnobazar.entity.Seller;

import java.time.LocalDateTime;
import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SoldProductDto {
    private Long id;
    private Double expense;
    private Double howMuchSold;
    private Integer count;
    private ArrayList<ProductDto> soldProduct;
    private ArrayList<SellerDto> soldBy;
}
