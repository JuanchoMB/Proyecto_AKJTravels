package co.edu.uniquindio.application.dto.bookingDTO;

import co.edu.uniquindio.application.model.enums.Status;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;
import java.time.LocalDate;

public record SearchBookingDTO(
        Status status,
        LocalDate checkIn,
        LocalDate checkOut,
        @Positive @Length(max = 60) Integer guest_number
) {
}
