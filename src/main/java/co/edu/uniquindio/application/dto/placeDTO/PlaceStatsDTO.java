package co.edu.uniquindio.application.dto.placeDTO;

public record PlaceStatsDTO(
        long reservations,     // número de reservas en el rango
        double averageRating   // promedio de rating (0.0 si no hay)
) {
}