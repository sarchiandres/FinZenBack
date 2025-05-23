package com.FinZenBack.ws.FinZenBack.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    /**
     * Envía un correo electrónico de notificación de inicio de sesión.
     *
     * @param to Dirección de correo electrónico del destinatario
     * @param nombre Nombre del usuario
     */
    public void sendLoginNotification(String to, String nombre) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("FinZen - Inicio de sesión detectado");
        message.setText(
                "Hola " + nombre + ",\n\n" +
                        "Hemos detectado un inicio de sesión en tu cuenta de FinZen.\n\n" +
                        "Fecha y hora: " + java.time.LocalDateTime.now() + "\n\n" +
                        "Si no fuiste tú, por favor cambia tu contraseña inmediatamente y contacta con nuestro equipo de soporte.\n\n" +
                        "Saludos,\n" +
                        "El equipo de FinZen");
        emailSender.send(message);
    }

    /**
     * Envía un correo electrónico con un token para restablecer la contraseña.
     *
     * @param to Dirección de correo electrónico del destinatario
     * @param nombre Nombre del usuario
     * @param token Token de restablecimiento
     */
    public void sendPasswordResetToken(String to, String nombre, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("FinZen - Restablecimiento de contraseña");
        message.setText(
                "Hola " + nombre + ",\n\n" +
                        "Has solicitado restablecer tu contraseña para tu cuenta de FinZen.\n\n" +
                        "Utiliza el siguiente código para restablecer tu contraseña: " + token + "\n\n" +
                        "Este código expirará en 60 minutos.\n\n" +
                        "Si no solicitaste este cambio, puedes ignorar este correo.\n\n" +
                        "Saludos,\n" +
                        "El equipo de FinZen");

        emailSender.send(message);
    }
}