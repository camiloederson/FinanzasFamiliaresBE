package com.mikadev.finanzasfamiliares.notification;

import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final EmailService emailService;

    public NotificationController(EmailService emailService) {
        this.emailService = emailService;
    }

    /** Enviar texto plano */
    @PostMapping("/email")
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequest req) {
        emailService.sendSimpleEmail(req.to(), req.subject(), req.text());
        return ResponseEntity.ok("Email enviado a " + req.to());
    }

    /** Enviar HTML */
    @PostMapping("/email/html")
    public ResponseEntity<String> sendEmailHtml(@RequestBody EmailHtmlRequest req) throws MessagingException {
        emailService.sendHtmlEmail(req.to(), req.subject(), req.html());
        return ResponseEntity.ok("Email HTML enviado a " + req.to());
    }

    /** Endpoint de prueba rápida desde navegador/Postman */
    @GetMapping("/test")
    public ResponseEntity<String> test(@RequestParam String to) {
        emailService.sendSimpleEmail(
                to,
                "Prueba de correo - FinanzasFamiliares",
                "¡Hola! Este es un correo de prueba desde FinanzasFamiliares365."
        );
        return ResponseEntity.ok("OK: correo de prueba enviado a " + to);
    }
}
