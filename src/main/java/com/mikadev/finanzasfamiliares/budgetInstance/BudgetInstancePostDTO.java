package com.mikadev.finanzasfamiliares.budgetInstance;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record BudgetInstancePostDTO(

        @NotNull(message = "El ID del mes presupuestario es obligatorio")
        @Min(value = 1, message = "El ID del mes presupuestario debe ser un número positivo")
        Long budgetMonthId,

        @NotNull(message = "El ID de la categoría es obligatorio")
        @Min(value = 1, message = "El ID de la categoría debe ser un número positivo")
        Long budgetCategoryId,

        @NotNull(message = "El monto planificado es obligatorio")
        @DecimalMin(value = "0.01", inclusive = true, message = "El monto planificado debe ser mayor que cero")
        BigDecimal plannedAmount,

        @Size(max = 500, message = "La descripción no debe exceder los 500 caracteres")
        String description
) {
}