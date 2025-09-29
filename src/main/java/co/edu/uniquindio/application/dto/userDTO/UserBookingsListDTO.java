package co.edu.uniquindio.application.dto.userDTO;

import co.edu.uniquindio.application.model.enums.BookingStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import java.time.LocalDateTime;

public record UserBookingsListDTO(
        BookingStatus bookingState,
        @FutureOrPresent LocalDateTime checkIn,
        @Future LocalDateTime checkOut,
        int guest_number
) {}
