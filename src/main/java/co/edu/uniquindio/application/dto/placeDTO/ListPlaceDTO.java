package co.edu.uniquindio.application.dto.placeDTO;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import org.hibernate.validator.constraints.Length;
import java.time.LocalDate;

public record ListPlaceDTO(
        @Length String city,
        @FutureOrPresent LocalDate checkIn,
        @Future LocalDate checkOut,
        Integer guest_number
) {
}
