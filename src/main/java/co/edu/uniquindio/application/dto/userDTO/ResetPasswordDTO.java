package co.edu.uniquindio.application.dto.userDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResetPasswordDTO {

    // Setters
    // Getters
    @Email @NotBlank
    private String email;

    // Código/token de verificación
    @NotBlank
    private String code;

    @NotBlank
    @Size(min = 7, max = 50)
    private String newPassword;

    private String confirmPassword;

    public ResetPasswordDTO() {}

    public ResetPasswordDTO(String email, String code, String newPassword, String confirmPassword) {
        this.email = email;
        this.code = code;
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
    }

}
