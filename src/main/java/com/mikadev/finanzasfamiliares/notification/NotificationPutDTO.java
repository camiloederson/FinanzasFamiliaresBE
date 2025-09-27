package com.mikadev.finanzasfamiliares.notification;

public record NotificationPutDTO(
        Long userId,
        String title,
        String message,
        String notifyAt,
        Boolean read
) {}
