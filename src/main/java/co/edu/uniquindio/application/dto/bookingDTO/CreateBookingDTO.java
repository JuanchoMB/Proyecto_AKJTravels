package co.edu.uniquindio.application.dto.bookingDTO;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record CreateBookingDTO(@NotNull @FutureOrPresent LocalDateTime checkIn,
                               @NotNull @Future LocalDateTime checkOut,
                               @NotNull @Min(1) Integer guest_number

) {
}