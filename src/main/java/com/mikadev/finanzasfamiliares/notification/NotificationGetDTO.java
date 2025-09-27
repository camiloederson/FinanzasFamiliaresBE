package com.mikadev.finanzasfamiliares.notification;

public record NotificationGetDTO(
        Long id,
        Long userId,
        String title,
        String message,
        String notifyAt,
        boolean read,
        Long createdBy,
        Long updatedBy,
        String createdAt,
        String updatedAt
) {}
