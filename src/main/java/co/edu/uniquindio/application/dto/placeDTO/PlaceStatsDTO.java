package co.edu.uniquindio.application.dto.placeDTO;

public record PlaceStatsDTO( double averageRating,
                             long totalComments,
                             long totalReservations,
                             double occupancyRate,
                             int cancellations,
                             double totalRevenue) {
}