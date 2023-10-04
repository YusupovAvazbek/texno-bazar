package texnobazar.texnobazar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import texnobazar.texnobazar.entity.Product;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    Optional<Product> getProductByNameAndPriceUSD(String productName, Double priceU);
}
