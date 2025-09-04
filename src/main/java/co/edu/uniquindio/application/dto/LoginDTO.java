package co.edu.uniquindio.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record LoginDTO(
        @NotEmpty(message = "Email vacio") @Email String email,
        @NotEmpty String password
){

}
