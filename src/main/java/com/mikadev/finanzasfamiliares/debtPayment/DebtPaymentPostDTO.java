package com.mikadev.finanzasfamiliares.debtPayment;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record DebtPaymentPostDTO(
        @NotNull(message = "El ID de la deuda es obligatorio")
        @Min(value = 1, message = "El ID de la deuda debe ser positivo")
        Long debtId,

        @NotNull(message = "El monto del pago es obligatorio")
        @DecimalMin(value = "0.0", inclusive = false, message = "El monto debe ser mayor que cero")
        BigDecimal amount,

        @NotNull(message = "La fecha del pago es obligatoria")
        LocalDate date,

        @NotNull(message = "El ID de la cuenta bancaria es obligatorio")
        @Min(value = 1, message = "El ID de la cuenta bancaria debe ser positivo")
        Long bankAccountId,

        @Min(value = 1, message = "El ID del gasto asociado debe ser positivo")
        Long expenseId // Opcional (puede ser null)
) {
}