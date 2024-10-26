package texnobazar.texnobazar.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import texnobazar.texnobazar.dto.StatisticsDto;
import texnobazar.texnobazar.repository.CustomRepositoryImpl;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequiredArgsConstructor
public class StatisticsController {
    private final CustomRepositoryImpl customRepository;
    @GetMapping("/api/analytics")
    public String get(){
        return "statistics/statistics";
    }
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    @GetMapping("/api/statistics")
    public @ResponseBody StatisticsDto getStatistics(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                                                     @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        StatisticsDto periodStatistics = customRepository.getPeriodStatistics(startDate, endDate);
        return periodStatistics;
    }
}
