package co.edu.uniquindio.application.services.impl;

import co.edu.uniquindio.application.dto.externalServiceDTO.SendEmailDTO;
import co.edu.uniquindio.application.dto.userDTO.RequestResetPasswordDTO;
import co.edu.uniquindio.application.dto.userDTO.ResetPasswordDTO;
import co.edu.uniquindio.application.exceptions.ValueConflictException;
import co.edu.uniquindio.application.model.PasswordResetCode;
import co.edu.uniquindio.application.model.User;
import co.edu.uniquindio.application.repositories.PasswordResetCodeRepository;
import co.edu.uniquindio.application.repositories.UserRepository;
import co.edu.uniquindio.application.services.EmailService;
import co.edu.uniquindio.application.services.PasswordResetService;
import co.edu.uniquindio.application.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PasswordResetServiceImpl implements PasswordResetService {

    private final PasswordResetCodeRepository passwordResetCodeRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void requestPasswordReset(RequestResetPasswordDTO requestResetPasswordDTO) throws Exception {
        // 1) validar usuario
        User user = userService.findByEmail(requestResetPasswordDTO.email());

        // 2) generar código seguro de 6 dígitos
        String code = generarCodigoSeguro();

        // 3) persistir código con validez 15 minutos
        PasswordResetCode prc = new PasswordResetCode();
        prc.setUsed(false);
        prc.setUser(user);
        prc.setCode(code);
        prc.setCreatedAt(LocalDateTime.now());
        prc.setExpiresAt(LocalDateTime.now().plusMinutes(15)); // 15 min
        passwordResetCodeRepository.save(prc);

        // 4) enviar email
        String body = """
                Hola %s,

                Tu código de verificación es: %s
                Este código vence en 15 minutos.

                Si no solicitaste este cambio, ignora este correo.
                """.formatted(user.getName(), code);

        emailService.sendMail(
                new SendEmailDTO("Cambio de la contraseña", body, requestResetPasswordDTO.email())
        );

        log.info("[PasswordReset] Code {} created for {} expiring at {}", code, user.getEmail(), prc.getExpiresAt());
    }

    @Override
    @Transactional
    public void resetPassword(ResetPasswordDTO resetPasswordDTO) throws Exception {
        // 1) validar usuario
        User user = userService.findByEmail(resetPasswordDTO.email());

        // 2) buscar código asociado
        Optional<PasswordResetCode> codeOpt = passwordResetCodeRepository.findByCodeAndUser(resetPasswordDTO.code(), user);
        if (codeOpt.isEmpty()) {
            throw new Exception("Código inválido.");
        }

        PasswordResetCode resetCode = codeOpt.get();

        if (resetCode.isUsed()) {
            throw new Exception("El código ya fue utilizado.");
        }

        if (resetCode.getExpiresAt() != null && LocalDateTime.now().isAfter(resetCode.getExpiresAt())) {
            throw new Exception("El código ha expirado. Solicita uno nuevo.");
        }

        // 3) validar contraseña
        if (resetPasswordDTO.newPassword() == null || resetPasswordDTO.newPassword().length() < 6) {
            throw new ValueConflictException("La contraseña debe tener al menos 6 caracteres.");
        }

        // 4) actualizar password del usuario y marcar código como usado
        String hashedPassword = passwordEncoder.encode(resetPasswordDTO.newPassword());
        user.setPassword(hashedPassword);
        resetCode.setUsed(true);

        userRepository.save(user);
        passwordResetCodeRepository.save(resetCode);

        log.info("[PasswordReset] Password updated for {}", user.getEmail());
    }

    private String generarCodigoSeguro() {
        SecureRandom sr = new SecureRandom();
        int n = sr.nextInt(1_000_000);       // 0..999999
        return String.format("%06d", n);     // 6 dígitos con ceros a la izquierda
    }
}
