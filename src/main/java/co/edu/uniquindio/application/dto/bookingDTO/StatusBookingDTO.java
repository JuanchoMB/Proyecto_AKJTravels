package co.edu.uniquindio.application.dto.bookingDTO;

import jakarta.validation.constraints.NotNull;

public record StatusBookingDTO(
        @NotNull StatusBookingDTO newStatus
) {
}