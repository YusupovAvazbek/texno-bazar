package texnobazar.texnobazar.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import texnobazar.texnobazar.dto.ProductDto;
import texnobazar.texnobazar.dto.StatisticsDto;
import texnobazar.texnobazar.entity.SoldProduct;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomRepositoryImpl implements CustomRepository {
    private final EntityManager entityManager;

    @Override
    public List<ProductDto> search(String query, List<String> filter, String sorting, String ordering, Integer size, Integer currentPage) {
        return null;
    }

    @Override
    public List<SoldProduct> getSoldProduct(String timePeriod) {
        String jpqlQuery = "SELECT * FROM sold_products s WHERE EXTRACT("+timePeriod+" FROM s.time_sold) = EXTRACT("+timePeriod+" FROM CURRENT_DATE) ORDER BY s.time_sold";

        Query nativeQuery = entityManager.createNativeQuery(jpqlQuery, SoldProduct.class);

        return nativeQuery.getResultList();

    }

    @Override
    public StatisticsDto getStatistics(String period) {
        String jpql = "SELECT \n" +
                "    SUM(s.how_much_sold * s.count) AS total_price,\n" +
                "    SUM(s.count) AS total_count,\n" +
                "\tSUM(s.expense) AS total_expense,\n" +
                "\tSUM((s.how_much_sold * s.count) - p.priceusd) AS total_profit\n" +
                "FROM sold_products s\n" +
                "JOIN products p ON s.sold_product_id = p.id\n" +
                "WHERE EXTRACT("+period+" FROM s.time_sold) = EXTRACT("+period+" FROM CURRENT_DATE)\n";
        Query nativeQuery = entityManager.createNativeQuery(jpql, Tuple.class);

        Tuple result = (Tuple) nativeQuery.getSingleResult();

        Double totalPrice = result.get("total_price", Double.class);
        Long totalCount = result.get("total_count", Long.class);
        Double totalExpense = result.get("total_expense", Double.class);
        Double totalProfit = result.get("total_profit", Double.class);

        return new StatisticsDto(totalPrice, totalCount, totalExpense, totalProfit);
    }

    @Override
    public StatisticsDto getPeriodStatistics(Date startDate, Date endDate) {

        String jpql = "SELECT " +
                "SUM(s.how_much_sold * s.count) AS total_price, " +
                "SUM(s.count) AS total_count, " +
                "SUM(s.expense) AS total_expense, " +
                "SUM((s.how_much_sold * s.count) - p.priceusd) AS total_profit " +
                "FROM sold_products s " +
                "JOIN products p ON s.sold_product_id = p.id " +
                "WHERE s.time_sold BETWEEN :startDate AND :endDate";

        Query jpqlQuery = entityManager.createNativeQuery(jpql, Tuple.class);
        jpqlQuery.setParameter("startDate", startDate);
        jpqlQuery.setParameter("endDate", endDate);

        Tuple result = (Tuple) jpqlQuery.getSingleResult();

        Double totalPrice = result.get("total_price", Double.class);
        Long totalCount = result.get("total_count", Long.class);
        Double totalExpense = result.get("total_expense", Double.class);
        Double totalProfit = result.get("total_profit", Double.class);

        return new StatisticsDto(totalPrice, totalCount, totalExpense, totalProfit);
    }
}
