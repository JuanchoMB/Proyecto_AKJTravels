package co.edu.uniquindio.application.dto.placeDTO;

import java.util.List;

public record PlaceDTO(
        String title,
        String description,
        int maxGuests,
        float nightlyPrice,
        List<String> images,
        List<String> services,
        String hostName
) {
}