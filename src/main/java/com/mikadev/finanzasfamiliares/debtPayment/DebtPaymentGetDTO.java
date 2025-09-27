package com.mikadev.finanzasfamiliares.debtPayment;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record DebtPaymentGetDTO(
        Long id,
        Long debtId,
        String debtCreditor, // Para conveniencia del cliente
        BigDecimal amount,
        LocalDate date,
        Long bankAccountId,
        String bankAccountName, // Para conveniencia del cliente
        Long expenseId,

        // Auditoría
        Long createdBy,
        Long updatedBy,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}