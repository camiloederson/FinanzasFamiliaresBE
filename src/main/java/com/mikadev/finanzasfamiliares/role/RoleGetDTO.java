package com.mikadev.finanzasfamiliares.role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RoleGetDTO(
        @NotNull(message = "El ID es obligatorio")
        Long id,

        @NotBlank(message = "El nombre del rol es obligatorio")
        String name,

        @NotNull(message = "El usuario creador es obligatorio")
        Long createdById,

        Long updatedById,

        @NotNull(message = "La fecha de creación es obligatoria")
        String createdAt,

        String updatedAt
) {}