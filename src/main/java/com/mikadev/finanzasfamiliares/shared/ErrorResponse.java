package com.mikadev.finanzasfamiliares.shared;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

record ErrorResponse(
        String message,
        HttpStatus status,
        LocalDateTime timestamp
) {}