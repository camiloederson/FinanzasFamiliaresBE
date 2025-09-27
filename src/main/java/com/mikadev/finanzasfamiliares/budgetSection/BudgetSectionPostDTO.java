package com.mikadev.finanzasfamiliares.budgetSection;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BudgetSectionPostDTO(
        @NotBlank(message = "El nombre de la sección es obligatorio")
        @Size(max = 100, message = "El nombre no debe exceder los 100 caracteres")
        String name,

        @Size(max = 500, message = "La descripción no debe exceder los 500 caracteres")
        String description
) {
}