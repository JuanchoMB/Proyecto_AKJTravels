package co.edu.uniquindio.application.dto.placeDTO;

import java.util.List;

public record PlaceDTO(String title,
                       double price,
                       String photo_url,
                       double average_rating,
                       String city
) {
}