package com.mikadev.finanzasfamiliares.notification;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/** DTO para HTML */
public record EmailHtmlRequest(
        @NotBlank @Email String to,
        @NotBlank String subject,
        @NotBlank String html
) { }
