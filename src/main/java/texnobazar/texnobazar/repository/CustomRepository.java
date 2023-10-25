package texnobazar.texnobazar.repository;

import org.springframework.data.domain.Page;
import texnobazar.texnobazar.dto.ProductDto;
import texnobazar.texnobazar.dto.SoldProductDto;
import texnobazar.texnobazar.dto.StatisticsDto;
import texnobazar.texnobazar.entity.SoldProduct;

import java.util.Date;
import java.util.List;

public interface CustomRepository {
    List<ProductDto> search(String query, List<String> filter, String sorting, String ordering, Integer size, Integer currentPage);
    List<SoldProduct> getSoldProduct(String query);
    StatisticsDto getStatistics(String period);
    StatisticsDto getPeriodStatistics(Date startDate, Date endDate);
}
