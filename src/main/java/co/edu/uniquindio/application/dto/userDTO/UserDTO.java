package co.edu.uniquindio.application.dto.userDTO;

import lombok.Builder;

@Builder
public record UserDTO(
        String id,
        String name,
        String email,
        String photoUrl,
        String role // ISO string
) {}
