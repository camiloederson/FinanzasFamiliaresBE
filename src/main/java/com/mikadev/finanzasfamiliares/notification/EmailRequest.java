package com.mikadev.finanzasfamiliares.notification;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


public record EmailRequest(
        @NotBlank @Email String to,
        @NotBlank String subject,
        @NotBlank String text
) { }