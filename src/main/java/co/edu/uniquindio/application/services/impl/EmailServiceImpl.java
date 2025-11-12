package co.edu.uniquindio.application.services.impl;

import co.edu.uniquindio.application.dto.externalServiceDTO.SendEmailDTO;
import co.edu.uniquindio.application.services.EmailService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    // Usa el mismo remitente configurado en spring.mail.username (Gmail)
    @Value("${spring.mail.username}")
    private String from;

    @Override
    @Async
    public void sendMail(SendEmailDTO sendEmailDTO) throws Exception {
        try {
            MimeMessage mime = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mime, "UTF-8");

            helper.setFrom(from); // IMPORTANTE: con Gmail el FROM debe ser la misma cuenta
            helper.setTo(sendEmailDTO.recipient());
            helper.setSubject(sendEmailDTO.subject());
            helper.setText(sendEmailDTO.body(), false); // texto plano

            mailSender.send(mime);
            log.info("Reset email sent to {}", sendEmailDTO.recipient());
        } catch (Exception e) {
            log.error("Error sending email to {}: {}", sendEmailDTO.recipient(), e.getMessage(), e);
            // Lanza una excepción clara para que el controlador devuelva 500 con mensaje legible
            throw new RuntimeException("No se pudo enviar el correo. Verifica las credenciales SMTP o la red.");
        }
    }
}
