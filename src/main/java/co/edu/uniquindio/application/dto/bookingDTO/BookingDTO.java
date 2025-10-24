package co.edu.uniquindio.application.dto.bookingDTO;

import co.edu.uniquindio.application.dto.userDTO.UserDTO;
import co.edu.uniquindio.application.model.enums.BookingState;
import java.time.LocalDate;

public record BookingDTO(
        BookingState bookingState,
        UserDTO user,
        LocalDate checkIn,
        LocalDate checkOut,
        int guest_number

) {
}