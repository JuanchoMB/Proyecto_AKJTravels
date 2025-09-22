package co.edu.uniquindio.application.dto;

import co.edu.uniquindio.application.model.Role;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Builder


public record UserDTO (
        String id,
        String name,
        String email,
        String photoUrl,
        Role role){

}
