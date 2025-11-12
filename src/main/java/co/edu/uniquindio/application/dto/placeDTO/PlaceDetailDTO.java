package co.edu.uniquindio.application.dto.placeDTO;

import co.edu.uniquindio.application.dto.userDTO.UserDetailDTO;
import co.edu.uniquindio.application.model.enums.Services;
import co.edu.uniquindio.application.model.enums.State;

import java.util.List;

public record PlaceDetailDTO(String id,
                             double latitude,
                             double longitude,
                             double price,
                             List<String> pics_url,
                             String description,
                             List<Services> services,
                             String title,
                             int capacity,
                             double averageRatings,
                             String street,
                             String neighborhood,
                             String city,
                             String department,
                             String country,
                             String postalCode,
                             UserDetailDTO userDetailDTO
) {}
