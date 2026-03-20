package com.mikadev.finanzasfamiliares.income;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record IncomePutDTO(

        @NotNull
        Long budgetMonthId,

        @NotNull
        @DecimalMin(value = "0.01")
        BigDecimal amount,

        @NotNull
        LocalDate incomeDate,

        @NotNull
        @Size(max = 150)
        String source,

        @Size(max = 500)
        String description
) {
}