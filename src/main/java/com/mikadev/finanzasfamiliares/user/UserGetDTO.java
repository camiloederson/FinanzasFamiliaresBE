package com.mikadev.finanzasfamiliares.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserGetDTO(
        @NotNull(message = "El ID es obligatorio")
        Long id,

        @NotBlank(message = "El nombre de usuario es obligatorio")
        String username,

        @NotBlank(message = "El email es obligatorio")
        @Email(message = "El email debe tener un formato válido")
        String email,

        @NotNull(message = "El rol es obligatorio")
        Long roleId,

        @NotNull(message = "El usuario creador es obligatorio")
        Long createdById,

        Long updatedById,

        @NotNull(message = "La fecha de creación es obligatoria")
        String createdAt,

        String updatedAt
) {}