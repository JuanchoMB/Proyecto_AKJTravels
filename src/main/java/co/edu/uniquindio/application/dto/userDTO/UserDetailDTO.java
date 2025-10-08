package co.edu.uniquindio.application.dto.userDTO;

import java.time.LocalDateTime;

public record UserDetailDTO(String id,
                            String name,
                            String photoUrl,
                            LocalDateTime createdAt) {
}
