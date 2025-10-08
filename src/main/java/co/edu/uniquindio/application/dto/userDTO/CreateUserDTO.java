package co.edu.uniquindio.application.dto.userDTO;

import co.edu.uniquindio.application.model.enums.Role;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record CreateUserDTO(@NotBlank(message = "Nombre requerido")
                            String name,
                            @NotBlank(message = "Apellido requerido")
                            String surname,
                            @Email(message = "Email inválido")
                            @NotBlank(message = "Email requerido")
                            String email,
                            @NotBlank(message = "Teléfono requerido")
                            String phone,
                            @NotNull(message = "Fecha de nacimiento requerida")
                            LocalDate birthDate,
                            @NotBlank(message = "País requerido")
                            String country,
                            String photoUrl,
                            @NotBlank(message = "Contraseña requerida")
                            @Size(min = 6, message = "Contraseña debe tener al menos 6 caracteres")
                            String password,
                            @NotNull Role role
) {
}