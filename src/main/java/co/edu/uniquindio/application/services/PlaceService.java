package co.edu.uniquindio.application.services;

import co.edu.uniquindio.application.dto.placeDTO.*;
import co.edu.uniquindio.application.model.enums.Amenities;

import java.util.List;

public interface PlaceService {

    void create(String id, CreatePlaceDTO createAccommodationDTO) throws Exception;
    void edit(String id, EditPlaceDTO editPlaceDTO) throws Exception;
    void delete(String id) throws Exception;
    // List<BookingDTO> listAll(ListBookingsDTO listBookingsDTO) throws Exception;
    List<PlaceDTO> search(ListPlaceDTO listPlaceDTO, int page) throws Exception;
    List<Amenities> listAllAmenities(String id) throws Exception;
    PlaceStatsDTO stats(String id, StatsDateDTO statsDateDTO) throws Exception;
    List<PlaceDTO> listAllPlacesHost(String id, int page) throws Exception;
    PlaceDetailDTO get(String id) throws Exception;
}