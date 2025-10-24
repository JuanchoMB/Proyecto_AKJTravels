package co.edu.uniquindio.application.dto.placeDTO;

import co.edu.uniquindio.application.dto.userDTO.UserDetailDTO;
import co.edu.uniquindio.application.model.enums.Services;
import java.util.List;

public record PlaceDetailDTO(String id, float latitude,
                             float longitude,
                             double price,
                             List<String> pics_url,
                             String description,
                             List<Services> services,
                             String title,
                             int capacity,
                             double averageRatings,
                             UserDetailDTO userDetailDTO
) {
}