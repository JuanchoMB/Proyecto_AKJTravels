package co.edu.uniquindio.application.services;

import co.edu.uniquindio.application.dto.bookingDTO.*;
import java.util.List;

public interface BookingService {

    void create(String id, CreateBookingDTO createBookingDTO);

    void create(CreateBookingDTO createBookingDTO) throws Exception;
    void delete(String id) throws Exception;
    void changeStatus(Long id, StatusBookingDTO statusBookingDTO) throws Exception;
    BookingDTO getById(Long id) throws Exception;
    List<ItemBookingDTO> getBookings(Long placeId) throws Exception;
    List<ItemBookingDTO> getBookingsUser(String userId) throws Exception;
    List<BookingDTO> listBookings(String id, SearchBookingDTO searchBookingDTO) throws Exception;
}
