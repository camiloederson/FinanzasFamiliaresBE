package com.mikadev.finanzasfamiliares.reports;

import java.math.BigDecimal;

public class ReportMapper {

    public MonthlyReportGetDTO monthlyDto(BigDecimal spentAmount, BigDecimal budgetedAmount, BigDecimal availableAmount) {
        return new MonthlyReportGetDTO(
                spentAmount, budgetedAmount, availableAmount
        );
    }
}
