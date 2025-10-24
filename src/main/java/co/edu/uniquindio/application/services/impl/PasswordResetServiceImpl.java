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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class PasswordResetServiceImpl implements PasswordResetService {
    private final PasswordResetCodeRepository passwordResetCodeRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final UserRepository userRepository;

    @Override
    public void requestPasswordReset(RequestResetPasswordDTO requestResetPasswordDTO) throws Exception {

        String code = generarCodigoAleatorio();
        User user = userService.findByEmail(requestResetPasswordDTO.email());

        PasswordResetCode prc = new PasswordResetCode();
        prc.setUsed(false);
        prc.setUser(user);
        prc.setCode(code);
        prc.setCreatedAt(LocalDateTime.now());
        prc.setExpiresAt(LocalDateTime.now().plusSeconds(900));

        passwordResetCodeRepository.save(prc);

        emailService.sendMail(new SendEmailDTO("Cambio de la contraseña", "Utiliza este codigo para hacer el cambio de tu contraseña: "+code, requestResetPasswordDTO.email()));

    }

    @Override
    @Transactional
    public void resetPassword(ResetPasswordDTO resetPasswordDTO) throws Exception {

        User user = userService.findByEmail(resetPasswordDTO.email());

        Optional<PasswordResetCode> code = passwordResetCodeRepository.findByCodeAndUser(resetPasswordDTO.code(), user);

        if(code.isEmpty()){
            throw new Exception("No se encuentra el code");
        }
        PasswordResetCode resetCode = code.get();

        if(resetCode.isUsed()){
            throw new Exception("El code ya esta usado");
        }
        if(resetCode.getExpiresAt() != null && LocalDateTime.now().isAfter(resetCode.getExpiresAt())){
            throw new Exception("El codigo ha expirado.Solicita uno nuevo");
        }
        if(resetPasswordDTO.newPassword() == null || resetPasswordDTO.newPassword().length() < 6){
            throw new ValueConflictException("La contraseña debe tener al menos 6 caracteres");
        }
        String hashedPassword = passwordEncoder.encode(resetPasswordDTO.newPassword());


        user.setPassword(hashedPassword);
        resetCode.setUsed(true);
        userRepository.save(user);
        passwordResetCodeRepository.save(resetCode);

    }


    private String generarCodigoAleatorio() {
        return String.valueOf((int)(Math.random() * 900000) + 100000);
    }
}