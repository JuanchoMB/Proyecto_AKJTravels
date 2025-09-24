package co.edu.uniquindio.application.dto.userDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserDTO(
        @NotBlank String name,
        @NotBlank @Email String email,
        @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
        @NotBlank String password,
        String phone,
        String birthDate // "2000-01-01" (ISO). Lo parseamos en el servicio
) {}
