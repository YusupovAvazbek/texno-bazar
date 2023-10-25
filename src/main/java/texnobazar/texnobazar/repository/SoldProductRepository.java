package texnobazar.texnobazar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import texnobazar.texnobazar.entity.SoldProduct;

import java.util.List;

@Repository
public interface SoldProductRepository extends JpaRepository<SoldProduct, Long> {
    @Query(value = "SELECT s from soldProducts s order by s.id")
    List<SoldProduct> getAllSoldProducts();
}
