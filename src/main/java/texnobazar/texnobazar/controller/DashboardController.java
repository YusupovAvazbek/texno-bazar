package texnobazar.texnobazar.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import texnobazar.texnobazar.dto.StatisticsDto;
import texnobazar.texnobazar.repository.CustomRepositoryImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class DashboardController {
    private final CustomRepositoryImpl customRepository;
    @GetMapping("/")
    public String index(){
        return "dashboard/dashboard";
    }
    @GetMapping("/api/profitData")
    @ResponseBody
    @Transactional
    public Map<String, Map<String, Double>> getProfitData() {

        Map<String, Map<String, Double>> profitData = new HashMap<>();
        StatisticsDto day = customRepository.getStatistics("DAY");
        Map<String, Double> daily = new HashMap<>();
        daily.put("total_price", (day.getTotalPrice() == null ? 0D : day.getTotalPrice()));
        daily.put("count", (day.getTotalCount() == null ? 0D : day.getTotalCount()));
        daily.put("expense", (day.getTotalExpense() == null ? 0D : day.getTotalExpense()));

        StatisticsDto week = customRepository.getStatistics("WEEK");
        Map<String, Double> weekly = new HashMap<>();
        weekly.put("total_price", week.getTotalPrice());
        weekly.put("count", (week.getTotalCount() == null ? 0D : week.getTotalCount()));
        weekly.put("expense",week.getTotalExpense());

        StatisticsDto month = customRepository.getStatistics("MONTH");
        Map<String, Double> monthly = new HashMap<>();
        monthly.put("total_price", month.getTotalPrice());
        monthly.put("count",  month.getTotalCount().doubleValue());
        monthly.put("expense",month.getTotalExpense());

        StatisticsDto year = customRepository.getStatistics("YEAR");
        Map<String, Double> yearly = new HashMap<>();
        yearly.put("total_price", year.getTotalPrice());
        yearly.put("count",  year.getTotalCount().doubleValue());
        yearly.put("expense", year.getTotalExpense());

        profitData.put("daily", daily);
        profitData.put("weekly", weekly);
        profitData.put("monthly", monthly);
        profitData.put("yearly", yearly);

        return profitData;
    }

}
