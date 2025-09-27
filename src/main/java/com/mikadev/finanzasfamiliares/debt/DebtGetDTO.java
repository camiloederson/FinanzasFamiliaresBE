package com.mikadev.finanzasfamiliares.debt;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record DebtGetDTO(
        Long id,
        String creditor,
        BigDecimal originalAmount,
        BigDecimal currentBalance,
        BigDecimal interestRate,
        LocalDate dueDate,
        String description,
        String status,
        boolean deleted,

        // Auditoría
        Long createdBy,
        Long updatedBy,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}