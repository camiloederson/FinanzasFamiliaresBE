package com.mikadev.finanzasfamiliares.expense;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record ExpensePostDTO(
        @NotNull(message = "El ID de la instancia de presupuesto es obligatorio")
        @Min(value = 1, message = "El ID debe ser un número positivo")
        Long budgetInstanceId,

        @NotNull(message = "El monto del gasto es obligatorio")
        @DecimalMin(value = "0.0", inclusive = false, message = "El monto debe ser mayor que cero")
        BigDecimal amount,

        @Size(max = 500, message = "La descripción no debe exceder los 500 caracteres")
        String description,

        @NotNull(message = "El ID de la cuenta bancaria es obligatorio")
        @Min(value = 1, message = "El ID debe ser un número positivo")
        Long bankAccountId,

        @NotNull(message = "El año es obligatorio")
        @Min(value = 2000, message = "El año debe ser válido")
        Integer yearRelated,

        @NotNull(message = "El mes es obligatorio")
        @Min(value = 1, message = "El mes debe ser un número entre 1 y 12")
        @Max(value = 12, message = "El mes debe ser un número entre 1 y 12")
        Integer monthRelated
) {
}