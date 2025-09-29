package co.edu.uniquindio.application.dto.userDTO;

import jakarta.validation.constraints.NotBlank;

public record ChangePasswordDTO(
        String idUser,
        @NotBlank String oldPassword,
        @NotBlank String newPassword
) {
}
