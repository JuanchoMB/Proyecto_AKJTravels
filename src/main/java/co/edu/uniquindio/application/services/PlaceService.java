package co.edu.uniquindio.application.services;

import co.edu.uniquindio.application.dto.bookingDTO.ListBookingsDTO;
import co.edu.uniquindio.application.dto.placeDTO.*;
import co.edu.uniquindio.application.dto.bookingDTO.BookingDTO;
import co.edu.uniquindio.application.model.enums.Amenities;

import java.util.List;

public interface PlaceService {

    void create(CreatePlaceDTO placeDTO) throws Exception;
    void edit(Long id, EditPlaceDTO placeDTO) throws Exception;
    void delete(Long id) throws Exception;
    PlaceDTO getById(Long id) throws Exception;
    MetricsDTO getMetricsById(Long id) throws Exception;
    List<ItemPlaceDTO> getPlacesUser(String id) throws Exception;

    List<BookingDTO> listAll(ListBookingsDTO listBookingsDTO) throws Exception;
    List<PlaceDTO> search(ListPlaceDTO listPlaceDTO) throws Exception;
    List<Amenities> listAllAmenities(String id) throws Exception;
    AccommodationStatsDTO stats(String id) throws Exception;
    List<PlaceDTO> listAllPlacesHost(String id) throws Exception;
}
