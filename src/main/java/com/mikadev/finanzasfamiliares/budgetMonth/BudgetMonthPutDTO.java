package com.mikadev.finanzasfamiliares.budgetMonth;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record BudgetMonthPutDTO(
        @NotNull(message = "El año es obligatorio")
        @Min(value = 2000, message = "El año debe ser mayor a 2000")
        Integer year,

        @NotNull(message = "El mes es obligatorio")
        @Min(value = 1, message = "El mes debe estar entre 1 y 12")
        Integer month,

        @NotNull(message = "El monto total es obligatorio")
        @Positive(message = "El monto debe ser mayor que 0")
        BigDecimal totalAmount,

        String description,

        @NotNull(message = "Debe indicar si el mes está cerrado o no")
        Boolean closed,

        @NotNull(message = "El usuario actualizador es obligatorio")
        Long updatedById
) {}

