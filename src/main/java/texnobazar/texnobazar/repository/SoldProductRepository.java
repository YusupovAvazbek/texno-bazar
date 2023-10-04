package texnobazar.texnobazar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import texnobazar.texnobazar.entity.SoldProduct;
@Repository
public interface SoldProductRepository extends JpaRepository<SoldProduct, Long> {
}
