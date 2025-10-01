package com.mikadev.finanzasfamiliares.reports;

import java.math.BigDecimal;

public record CategoryReportGetDTO(
        String categoryName,
        BigDecimal budgetedAmount,
        BigDecimal spentAmount,
        BigDecimal availableAmount
) {
}
