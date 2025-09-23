package co.edu.uniquindio.application.services;

import co.edu.uniquindio.application.dto.bookingDTO.BookingDTO;
import co.edu.uniquindio.application.dto.bookingDTO.CreateBookingDTO;
import co.edu.uniquindio.application.dto.bookingDTO.SearchBookingDTO;
import java.util.List;

public interface BookingService {

    void create(String id, CreateBookingDTO createBookingDTO);
    void delete(String id) throws Exception;
    List<BookingDTO> listBookings(String id, SearchBookingDTO searchBookingDTO) throws Exception;
}
