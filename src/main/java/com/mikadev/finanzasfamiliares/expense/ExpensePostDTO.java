package com.mikadev.finanzasfamiliares.expense;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record ExpensePostDTO(

        @NotNull
        Long budgetMonthId,

        @NotNull
        Long budgetCategoryId,

        @NotNull
        @DecimalMin(value = "0.01")
        BigDecimal amount,

        @NotNull
        Long bankAccountId,

        @NotNull
        LocalDate date,

        @Size(max = 500)
        String description
) {
}