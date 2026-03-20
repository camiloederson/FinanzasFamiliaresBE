package com.mikadev.finanzasfamiliares.budgetMonth;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record BudgetMonthPostDTO(

        @NotNull(message = "El año es obligatorio")
        @Min(value = 2000, message = "El año debe ser mayor o igual a 2000")
        Integer year,

        @NotNull(message = "El mes es obligatorio")
        @Min(value = 1, message = "El mes debe estar entre 1 y 12")
        @Max(value = 12, message = "El mes debe estar entre 1 y 12")
        Integer month,

        @NotNull(message = "El monto planificado es obligatorio")
        @Positive(message = "El monto planificado debe ser mayor que 0")
        BigDecimal totalPlanned,

        String description
) {
}