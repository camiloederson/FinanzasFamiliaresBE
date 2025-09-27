package com.mikadev.finanzasfamiliares.budgetSection;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BudgetSectionPutDTO(
        @NotNull(message = "El ID es obligatorio para la actualización")
        @Min(value = 1, message = "El ID debe ser positivo")
        Long id,

        @NotBlank(message = "El nombre de la sección es obligatorio")
        @Size(max = 100, message = "El nombre no debe exceder los 100 caracteres")
        String name,

        @Size(max = 500, message = "La descripción no debe exceder los 500 caracteres")
        String description
) {
}