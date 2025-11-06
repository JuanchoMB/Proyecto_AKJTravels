package co.edu.uniquindio.application.dto.placeDTO;

public record PlaceDTO(String id,
                       String title,
                       double price,
                       String photo_url,
                       double average_rating,
                       String city) { }
