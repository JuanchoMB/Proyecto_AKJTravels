package co.edu.uniquindio.application.dto.placeDTO;

public record ItemPlaceDTO(
        Long id,
        String title,
        float nightlyPrice,
        float rating,
        String coverImage
) {
}