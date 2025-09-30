package com.mikadev.finanzasfamiliares.reports;

import java.math.BigDecimal;

public record MonthlyReportGetDTO(
        BigDecimal spentAmount,
        BigDecimal budgetedAmount,
        BigDecimal availableAmount
) {
}
