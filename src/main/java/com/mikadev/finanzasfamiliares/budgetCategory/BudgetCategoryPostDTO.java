package com.mikadev.finanzasfamiliares.budgetCategory;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BudgetCategoryPostDTO(
        @NotBlank(message = "El nombre de la categoría es obligatorio")
        @Size(max = 100, message = "El nombre no debe exceder los 100 caracteres")
        String name,

        @Size(max = 500, message = "La descripción no debe exceder los 500 caracteres")
        String description,

        @NotNull(message = "El ID de la sección de presupuesto es obligatorio")
        @Min(value = 1, message = "El ID de la sección debe ser positivo")
        Long budgetSectionId
) {
}