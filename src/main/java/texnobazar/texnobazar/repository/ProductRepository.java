package texnobazar.texnobazar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import texnobazar.texnobazar.entity.Product;

import java.util.Optional;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    Optional<Product> getProductByNameAndPriceUSD(String productName, Double priceUSD);
    @Query(value = "SELECT p from products p order by p.id")
    List<Product> getAllProducts();
    List<Product> getProductByCategory_Id(Long id);

}
