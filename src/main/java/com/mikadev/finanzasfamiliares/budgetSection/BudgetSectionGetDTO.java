package com.mikadev.finanzasfamiliares.budgetSection;

import java.time.LocalDateTime;

public record BudgetSectionGetDTO(
        Long id,
        String name,
        String description,
        Boolean deleted,

        // Auditoría
        Long createdBy,
        Long updatedBy,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}