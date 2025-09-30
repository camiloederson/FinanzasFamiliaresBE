package com.mikadev.finanzasfamiliares.budgetMonth;

import java.math.BigDecimal;

public record BudgetMonthGetDTO(
        Long id,
        Integer year,
        Integer month,
        BigDecimal totalAmount,
        BigDecimal totalSpent,
        String description,
        Boolean closed
) {}
