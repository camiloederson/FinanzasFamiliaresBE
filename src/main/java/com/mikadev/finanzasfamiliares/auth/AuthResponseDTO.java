package com.mikadev.finanzasfamiliares.auth;

public record AuthResponseDTO(
        String username,
        String token
) {
}
