package com.mikadev.finanzasfamiliares.auth;

public record AuthRequestDTO(
        String username,
        String password
) {
}
