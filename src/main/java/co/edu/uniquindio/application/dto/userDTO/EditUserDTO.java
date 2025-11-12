package co.edu.uniquindio.application.dto.userDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

public record EditUserDTO(
        @NotBlank @Length(max = 100) String name,
        @Length(max = 100) String lastName,
        @Length(max = 15)  String phone,
        @Length(max = 300) String photoUrl,
        @Past              LocalDate birthDate   // <-- SIN @NotNull: ahora es opcional
) {}
