package co.edu.uniquindio.application.dto.placeDTO;

import java.time.LocalDate;

public record PlaceStatsDTO(double averageRating,
                            long totalComments,
                            long totalReservations,
                            double occupancyRate,
                            int cancellations,
                            double totalRevenue) {
}