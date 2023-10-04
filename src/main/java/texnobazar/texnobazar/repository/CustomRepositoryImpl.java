package texnobazar.texnobazar.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import texnobazar.texnobazar.dto.ProductDto;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomRepositoryImpl implements CustomRepository {
    private final EntityManager entityManager;
    @Override
    public Page<ProductDto> universalSearch(String query, List<String> filter, String sorting, String ordering, Integer size, Integer currentPage) {
        return null;
    }
}
