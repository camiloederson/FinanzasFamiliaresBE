package com.mikadev.finanzasfamiliares.income;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record IncomePostDTO(
        @NotNull(message = "El monto es obligatorio")
        @DecimalMin(value = "0.0", inclusive = false, message = "El monto debe ser mayor que cero")
        BigDecimal amount,

        @NotNull(message = "La fecha es obligatoria")
        LocalDate date,

        @NotNull(message = "El año es obligatorio")
        @Min(value = 2000, message = "El año debe ser válido")
        Integer yearRelated,

        @NotNull(message = "El mes es obligatorio")
        @Min(value = 1, message = "El mes debe ser un número entre 1 y 12")
        @Max(value = 12, message = "El mes debe ser un número entre 1 y 12")
        Integer monthRelated,

        @Size(max = 500, message = "La descripción no debe exceder los 500 caracteres")
        String description,

        @Size(max = 200, message = "El cliente/fuente no debe exceder los 200 caracteres")
        String customer,

        @Size(max = 100, message = "La persona que recibió el ingreso no debe exceder los 100 caracteres")
        String receivedBy,

        @Size(max = 50, message = "El tipo de ingreso no debe exceder los 50 caracteres")
        String incomeType,

        @NotNull(message = "El ID de la cuenta bancaria es obligatorio")
        @Min(value = 1, message = "El ID debe ser un número positivo")
        Long bankAccountId
) {
}