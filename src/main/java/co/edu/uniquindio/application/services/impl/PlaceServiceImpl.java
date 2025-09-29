package co.edu.uniquindio.application.services.impl;

import co.edu.uniquindio.application.dto.bookingDTO.ListBookingsDTO;
import co.edu.uniquindio.application.dto.placeDTO.*;
import co.edu.uniquindio.application.dto.bookingDTO.BookingDTO;
import co.edu.uniquindio.application.exceptions.ResourceNotFoundException;
import co.edu.uniquindio.application.exceptions.ValueConflictException;
import co.edu.uniquindio.application.mappers.AccommodationMapper;
import co.edu.uniquindio.application.mappers.ShowAccommodationMapper;
import co.edu.uniquindio.application.model.Place;
import co.edu.uniquindio.application.model.enums.Amenities;
import co.edu.uniquindio.application.services.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable_;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {

    private final AccommodationMapper placeMapper;
    private final Map<String, Place> placeStore = new ConcurrentHashMap<>();
    private final ShowAccommodationMapper showAccommodationMapper;

    @Override
    public void create(CreatePlaceDTO createPlaceDTO) throws Exception {
        if(verifyExistence(createPlaceDTO)){
            throw new ValueConflictException("Ya existe este alojamiento");
        }

        Place place1 = placeMapper.toEntity(createPlaceDTO);
        placeStore.put(String.valueOf(AbstractPersistable_.id), place1);
    }
    private boolean verifyExistence(CreatePlaceDTO createPlaceDTO) {
        /*
        for (Accommodation accommodation : accommodationStore.values()) {
            double distancia = GeoUtils.calcularDistancia(
                    createAccommodationDTO.latitude(), createAccommodationDTO.longitude(),
                    accommodation.getLocation().getCoordinates().latitude(),
                    accommodation.getLocation().getCoordinates().longitude()
            );

            if (distancia <= 5 && accommodation.getTitle().equals(createAccommodationDTO.title())) { // 10 metros de umbral
                return true;
            }
        }
        return false;

         */
        return false;
    }


    @Override
    public void edit(Long id, EditPlaceDTO placeDTO) throws Exception {

        Place place = placeStore.get(id);
        if(place == null){
            throw new ResourceNotFoundException("No se encontró el alojamiento");
        }
        placeStore.put(String.valueOf(id), place);
    }

    @Override
    public void delete(Long id) throws Exception {

        Place place = placeStore.get(String.valueOf(id));
        if(place == null){


        }
    }

    @Override
    public PlaceDTO getById(Long id) throws Exception {
        return null;
    }

    @Override
    public MetricsDTO getMetricsById(Long id) throws Exception {
        return null;
    }

    @Override
    public List<ItemPlaceDTO> getPlacesUser(String id) throws Exception {
        return List.of();
    }

    @Override
    public List<BookingDTO> listAll(ListBookingsDTO listBookingsDTO) throws Exception {

        return List.of();
    }


    // filtro de busqueda de los alojamientos
    @Override
    public List<PlaceDTO> search(ListPlaceDTO listPlaceDTO) throws Exception {

        /*
        // Si no viene ningún filtro, devuelvo todos directamente
        if ((listAccommodationDTO.city() == null || listAccommodationDTO.city().isBlank()) &&
                listAccommodationDTO.checkIn() == null &&
                listAccommodationDTO.checkOut() == null &&
                listAccommodationDTO.guest_number() == null) {
            return accommodationStore.values().stream()
                    .map(showAccommodationMapper::toAccommodationDTO)
                    .toList();
        }

        return accommodationStore.values().stream()
                // Filtro por ciudad
                .filter(acc -> listAccommodationDTO.city() == null ||
                        acc.getLocation().getCity().equalsIgnoreCase(listAccommodationDTO.city()))

                // Filtro por checkIn (todas las reservas)
                .filter(acc -> listAccommodationDTO.checkIn() == null ||
                        (!acc.getBookings().isEmpty() &&
                                acc.getBookings().stream().allMatch(
                                        b -> !b.getCheckIn().isBefore(listAccommodationDTO.checkIn())
                                )))
                // Filtro por checkOut (todas las reservas)
                .filter(acc -> listAccommodationDTO.checkOut() == null ||
                        (!acc.getBookings().isEmpty() &&
                                acc.getBookings().stream().allMatch(
                                        b -> !b.getCheckOut().isAfter(listAccommodationDTO.checkOut())
                                )))
                // Filtro por número de invitados (al menos una reserva que coincida)
                .filter(acc -> listAccommodationDTO.guest_number() == null ||
                        (!acc.getBookings().isEmpty() &&
                                acc.getBookings().stream().anyMatch(
                                        b -> Objects.equals(b.getGuest_number(), listAccommodationDTO.guest_number())
                                )))
                // Mapeo al DTO
                .map(showAccommodationMapper::toAccommodationDTO)
                .toList();*/
        return List.of();
    }


    @Override
    public List<Amenities> listAllAmenities(String id) throws Exception {

        Place place = placeStore.get(id);
        if(place == null){
            throw new ResourceNotFoundException("No se encontró el alojamiento");

        }

        return place.getAmenities();
    }


    @Override
    public AccommodationStatsDTO stats(String id) throws Exception {
        return null;
    }

    @Override
    public List<PlaceDTO> listAllPlacesHost(String id) throws Exception {
        return List.of();
    }
}
