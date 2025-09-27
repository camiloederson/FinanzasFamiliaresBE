package com.mikadev.finanzasfamiliares.debt;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record DebtPostDTO(
        @NotBlank(message = "El acreedor es obligatorio")
        @Size(max = 200, message = "El nombre del acreedor no debe exceder los 200 caracteres")
        String creditor,

        @NotNull(message = "El monto original es obligatorio")
        @DecimalMin(value = "0.01", message = "El monto debe ser mayor que cero")
        BigDecimal originalAmount,

        // El balance actual se puede omitir aquí si asumes que es igual al original al crear,
        // pero lo mantendremos para consistencia con la entidad y la validación.
        @NotNull(message = "El balance actual es obligatorio")
        @DecimalMin(value = "0.01", message = "El balance debe ser mayor que cero")
        BigDecimal currentBalance,

        @DecimalMin(value = "0.0", message = "La tasa de interés no puede ser negativa")
        @Digits(integer = 5, fraction = 2, message = "La tasa de interés debe tener hasta 5 dígitos enteros y 2 decimales")
        BigDecimal interestRate,

        @FutureOrPresent(message = "La fecha de vencimiento no puede ser en el pasado")
        LocalDate dueDate,

        @Size(max = 500, message = "La descripción no debe exceder los 500 caracteres")
        String description,

        @Size(max = 20, message = "El estado no debe exceder los 20 caracteres")
        String status
) {
}