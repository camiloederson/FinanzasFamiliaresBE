package com.mikadev.finanzasfamiliares.income;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record IncomeGetDTO(
        Long id,
        Long budgetMonthId,
        Integer budgetMonthYear,
        Integer budgetMonthMonth,
        BigDecimal amount,
        LocalDate incomeDate,
        String source,
        String description,

        Long createdBy,
        Long updatedBy,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}