package co.edu.uniquindio.application.dto.userDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

public record ChangePasswordDTO(
        @NotBlank @Size (min = 7, max = 20) String oldPassword,
        @NotBlank @Length(min = 7, max = 20) String newPassword
) {
}
