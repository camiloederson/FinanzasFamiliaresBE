package com.mikadev.finanzasfamiliares.income;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record IncomeGetDTO(
        Long id,
        BigDecimal amount,
        LocalDate date,
        Integer yearRelated,
        Integer monthRelated,
        String description,
        String customer,
        String receivedBy,
        String incomeType,
        Long bankAccountId,
        String bankAccountName,

        // Auditoría
        Long createdBy,
        Long updatedBy,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}