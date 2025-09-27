package com.mikadev.finanzasfamiliares.budgetInstance;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record BudgetInstancePostDTO(
        @NotNull(message = "El ID de la categoría es obligatorio")
        @Min(value = 1, message = "El ID de la categoría debe ser un número positivo")
        Long budgetCategoryId,

        @NotNull(message = "El monto presupuestado es obligatorio")
        @DecimalMin(value = "0.0", inclusive = false, message = "El monto debe ser mayor que cero")
        BigDecimal amount,

        @NotNull(message = "El año es obligatorio")
        @Min(value = 2000, message = "El año debe ser válido")
        Integer yearRelated,

        @NotNull(message = "El mes es obligatorio")
        @Min(value = 1, message = "El mes debe ser un número entre 1 y 12")
        @Max(value = 12, message = "El mes debe ser un número entre 1 y 12")
        Integer monthRelated,

        @Size(max = 500, message = "La descripción no debe exceder los 500 caracteres")
        String description
) {
}