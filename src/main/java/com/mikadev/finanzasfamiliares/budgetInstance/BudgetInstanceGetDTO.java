package com.mikadev.finanzasfamiliares.budgetInstance;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BudgetInstanceGetDTO(
        Long id,
        Long budgetMonthId,
        Integer budgetMonthYear,
        Integer budgetMonthMonth,
        Long budgetCategoryId,
        String budgetCategoryName,
        BigDecimal plannedAmount,
        BigDecimal spentAmount,
        String description,
        Boolean deleted,
        Long createdBy,
        Long updatedBy,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}