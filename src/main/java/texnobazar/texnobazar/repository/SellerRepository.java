package texnobazar.texnobazar.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import texnobazar.texnobazar.entity.Product;
import texnobazar.texnobazar.entity.Seller;

import java.util.List;
import java.util.Optional;

@Repository
public interface SellerRepository extends CrudRepository<Seller,Long> {
    Optional<Seller> getSellerByUsername(String username);
    Optional<Seller> getSellerByIdAndIsActive(Long id,short isActive);
    @Query(value = "SELECT s from sellers s where s.isActive = 1 order by s.id")
    List<Seller> getAllSellers();
}
