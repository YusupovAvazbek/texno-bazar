package texnobazar.texnobazar.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StatisticsDto {
    Double totalPrice;
    Long totalCount;
    Double totalExpense;
    Double totalProfit;
}
