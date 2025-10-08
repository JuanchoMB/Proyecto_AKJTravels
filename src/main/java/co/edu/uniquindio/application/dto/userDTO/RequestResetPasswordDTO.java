package co.edu.uniquindio.application.dto.userDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RequestResetPasswordDTO(@NotBlank(message = "El email es requerido")
                                      @Email(message = "Email inválido")
                                      String email
) {
}
