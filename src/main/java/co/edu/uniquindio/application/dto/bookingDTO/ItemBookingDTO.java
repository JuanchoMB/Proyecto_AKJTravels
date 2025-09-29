package co.edu.uniquindio.application.dto.bookingDTO;

import java.time.LocalDateTime;

public record ItemBookingDTO(
        Long id,
        Long placeId,
        String placeName,
        String placeCity,
        LocalDateTime createdAt,
        LocalDateTime checkIn,
        LocalDateTime checkOut,
        int guestCount,
        float price,
        String status
) {
}