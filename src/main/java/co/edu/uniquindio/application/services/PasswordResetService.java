package co.edu.uniquindio.application.services;

import co.edu.uniquindio.application.dto.userDTO.RequestResetPasswordDTO;
import co.edu.uniquindio.application.dto.userDTO.ResetPasswordDTO;
import co.edu.uniquindio.application.model.User;

public interface PasswordResetService {

    void requestPasswordReset(RequestResetPasswordDTO requestResetPasswordDTO) throws Exception;
    void resetPassword(ResetPasswordDTO resetPasswordDTO) throws Exception;
}