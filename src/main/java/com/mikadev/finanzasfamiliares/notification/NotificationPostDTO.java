package com.mikadev.finanzasfamiliares.notification;

public record NotificationPostDTO(
        Long userId,
        String title,
        String message,
        String notifyAt,
        Boolean read
) {}
