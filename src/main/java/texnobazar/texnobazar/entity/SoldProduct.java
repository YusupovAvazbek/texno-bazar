package texnobazar.texnobazar.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Entity(name = "soldProducts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SoldProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double expense;
    private Double howMuchSold;
    private Integer count;
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Product soldProduct;
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Seller soldBy;
    private LocalDateTime timeSold;
}
