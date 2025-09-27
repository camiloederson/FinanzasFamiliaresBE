package com.mikadev.finanzasfamiliares.bankAccount;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BankAccountGetDTO(
        Long id,
        String name,
        BigDecimal balance,
        String bank,
        String purpose,
        String ownedBy,
        String usedBy,
        Boolean deleted,

        Long createdBy,
        Long updatedBy,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}