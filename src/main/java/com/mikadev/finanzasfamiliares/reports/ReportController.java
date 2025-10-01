package com.mikadev.finanzasfamiliares.reports;

import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/reports")
@CrossOrigin(origins = "http://localhost:4200")

public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/monthly/{year}/{month}")
    public MonthlyReportGetDTO monthlyReport(@PathVariable("year") int year,
                                             @PathVariable("month") int month) {
        return reportService.monthlyReport(year, month);
    }

    @GetMapping("/categories/{year}/{month}")
    public ResponseEntity<List<CategoryReportGetDTO>> categoryReport(@PathVariable int year,
                                                                     @PathVariable int month) {
        return ResponseEntity.ok(reportService.categoryReport(year, month));
    }

}
