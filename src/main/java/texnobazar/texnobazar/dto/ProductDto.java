package texnobazar.texnobazar.dto;

import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private Double priceUSD;
    private Double priceUZS;
    private Integer count;
    private CategoryDto category;
}
