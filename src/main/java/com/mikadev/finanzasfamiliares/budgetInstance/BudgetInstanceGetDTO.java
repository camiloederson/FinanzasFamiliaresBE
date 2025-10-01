package com.mikadev.finanzasfamiliares.budgetInstance;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BudgetInstanceGetDTO(
        Long id,
        Long budgetCategoryId,
        String budgetCategoryName, // Se añade el nombre para conveniencia del cliente
        BigDecimal amount,
        BigDecimal spentAmount,
        Integer yearRelated,
        Integer monthRelated,
        String description,
        Boolean deleted,
        Long createdBy,
        Long updatedBy,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}