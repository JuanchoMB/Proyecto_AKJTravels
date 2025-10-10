package co.edu.uniquindio.application.dto.bookingDTO;

import co.edu.uniquindio.application.model.enums.BookingState;
import java.time.LocalDateTime;

public record SearchBookingDTO(BookingState state,
                               LocalDateTime checkIn,
                               LocalDateTime checkOut,
                               Integer guest_number
) {
}