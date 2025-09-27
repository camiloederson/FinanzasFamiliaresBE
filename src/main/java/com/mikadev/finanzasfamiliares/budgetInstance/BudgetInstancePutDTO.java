package com.mikadev.finanzasfamiliares.budgetInstance;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record BudgetInstancePutDTO(
        @NotNull(message = "El ID de la instancia es obligatorio para la actualización")
        @Min(value = 1, message = "El ID de la instancia debe ser un número positivo")
        Long id,

        @NotNull(message = "El ID de la categoría es obligatorio")
        @Min(value = 1, message = "El ID de la categoría debe ser un número positivo")
        Long budgetCategoryId,

        @NotNull(message = "El monto presupuestado es obligatorio")
        @DecimalMin(value = "0.0", inclusive = false, message = "El monto debe ser mayor que cero")
        BigDecimal amount,

        @Size(max = 500, message = "La descripción no debe exceder los 500 caracteres")
        String description
) {
}