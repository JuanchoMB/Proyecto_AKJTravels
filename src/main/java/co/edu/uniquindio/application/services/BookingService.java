package co.edu.uniquindio.application.services;

import co.edu.uniquindio.application.dto.bookingDTO.BookingDTO;
import co.edu.uniquindio.application.dto.bookingDTO.CreateBookingDTO;
import co.edu.uniquindio.application.dto.bookingDTO.SearchBookingDTO;
import java.util.List;

public interface BookingService {

    void create(String id, String userId, CreateBookingDTO createBookingDTO) throws Exception;
    void delete(String id) throws Exception;

    //para ver la lista de reservas del alojamiento aplicando filtros y paginación
    List<BookingDTO> listBookings(String id, int page, SearchBookingDTO searchBookingDTO) throws Exception;

    ////para ver la lista de reservas del usuario aplicando filtros y paginación
    List<BookingDTO> listBookingsUser(String id, int page, SearchBookingDTO searchBookingDTO) throws Exception;
}