package co.edu.uniquindio.application.dto.userDTO;

import co.edu.uniquindio.application.model.enums.Role;
import lombok.Builder;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record UserDTO(
        String name,
        String lastName,      // ⬅️ NUEVO
        String email,
        String photoUrl,
        LocalDate birthDate,
        Role role,
        LocalDateTime createdAt
) {}
