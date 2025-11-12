package co.edu.uniquindio.application.dto.placeDTO;

import co.edu.uniquindio.application.model.enums.State;

public record PlaceDTO(String id,
                       String title,
                       double price,
                       String photo_url,
                       State state,
                       double average_rating,
                       String city) { }
