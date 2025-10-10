package co.edu.uniquindio.application.dto.bookingDTO;

import co.edu.uniquindio.application.model.enums.BookingState;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;
import java.time.LocalDateTime;

public record SearchBookingPlaceDTO(BookingState state,
                                    LocalDateTime checkIn,
                                    LocalDateTime checkOut,
                                    @Positive @Length(max = 60) Integer guest_number) {
}