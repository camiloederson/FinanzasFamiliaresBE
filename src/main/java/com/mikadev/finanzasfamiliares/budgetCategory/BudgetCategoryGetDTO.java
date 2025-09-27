package com.mikadev.finanzasfamiliares.budgetCategory;

import java.time.LocalDateTime;

public record BudgetCategoryGetDTO(
        Long id,
        String name,
        String description,
        Long budgetSectionId,
        String budgetSectionName,
        Boolean deleted,
        Long createdBy,
        Long updatedBy,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}