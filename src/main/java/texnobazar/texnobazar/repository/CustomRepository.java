package texnobazar.texnobazar.repository;

import org.springframework.data.domain.Page;
import texnobazar.texnobazar.dto.ProductDto;

import java.util.List;

public interface CustomRepository {
    Page<ProductDto> universalSearch(String query, List<String> filter, String sorting, String ordering, Integer size, Integer currentPage);
}
