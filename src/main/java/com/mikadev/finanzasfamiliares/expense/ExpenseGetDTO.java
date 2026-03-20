package com.mikadev.finanzasfamiliares.expense;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record ExpenseGetDTO(
        Long id,
        Long budgetMonthId,
        Integer budgetMonthYear,
        Integer budgetMonthMonth,
        Long budgetCategoryId,
        String budgetCategoryName,
        BigDecimal amount,
        String description,
        Long bankAccountId,
        String bankAccountName,
        LocalDate date,
        Long createdBy,
        Long updatedBy,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}