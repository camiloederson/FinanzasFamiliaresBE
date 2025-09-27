package com.mikadev.finanzasfamiliares.role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record RolePostDTO(
        @NotBlank(message = "El nombre del rol es obligatorio")
        @Size(min = 2, max = 50, message = "El nombre del rol debe tener entre 2 y 50 caracteres")
        String name,

        @NotNull(message = "El usuario creador es obligatorio")
        @Positive(message = "El ID del usuario creador debe ser positivo")
        Long createdById
) {}