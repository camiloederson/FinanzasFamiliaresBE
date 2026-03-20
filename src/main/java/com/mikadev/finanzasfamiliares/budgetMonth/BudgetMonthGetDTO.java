package com.mikadev.finanzasfamiliares.budgetMonth;

import java.math.BigDecimal;

public record BudgetMonthGetDTO(
        Long id,
        Integer year,
        Integer month,
        BigDecimal totalPlanned,
        BigDecimal totalIncome,
        BigDecimal totalExpense,
        BigDecimal remainingBalance,
        String description,
        Boolean closed
) {
}