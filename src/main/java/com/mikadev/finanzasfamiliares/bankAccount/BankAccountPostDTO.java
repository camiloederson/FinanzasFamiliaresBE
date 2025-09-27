package com.mikadev.finanzasfamiliares.bankAccount;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record BankAccountPostDTO(
        @NotBlank(message = "Name is required")
        @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
        String name,

        @NotNull(message = "Balance is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Balance must be greater than zero")
        BigDecimal balance,

        @Size(max = 100, message = "Bank must not exceed 100 characters")
        String bank,

        @Size(max = 200, message = "Purpose must not exceed 200 characters")
        String purpose,

        @Size(max = 100, message = "OwnedBy must not exceed 100 characters")
        String ownedBy,

        @Size(max = 100, message = "UsedBy must not exceed 100 characters")
        String usedBy
) {
}