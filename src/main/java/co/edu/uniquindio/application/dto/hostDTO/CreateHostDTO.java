package co.edu.uniquindio.application.dto.hostDTO;

import co.edu.uniquindio.application.model.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

public record CreateHostDTO(
        @NotNull @Length(max = 100) String name,
        @Email String email,
        @Length(max = 10) String phone,
        @Past @NotNull LocalDate birthDate,
        @NotNull String country,
        @NotBlank @Length(min = 7, max = 20) String password,
        @Length(max = 300) String photoUrl,
        @NotNull Role role
) {
}
