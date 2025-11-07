package co.edu.uniquindio.application.services.impl;

import co.edu.uniquindio.application.dto.placeDTO.*;
import co.edu.uniquindio.application.exceptions.BadRequestException;
import co.edu.uniquindio.application.exceptions.ResourceNotFoundException;
import co.edu.uniquindio.application.exceptions.UnauthorizedException;
import co.edu.uniquindio.application.exceptions.ValueConflictException;
import co.edu.uniquindio.application.mappers.*;
import co.edu.uniquindio.application.model.Booking;
import co.edu.uniquindio.application.model.Place;
import co.edu.uniquindio.application.model.User;
import co.edu.uniquindio.application.model.enums.Services;
import co.edu.uniquindio.application.model.enums.BookingState;
import co.edu.uniquindio.application.model.enums.State;
import co.edu.uniquindio.application.repositories.BookingRepository;
import co.edu.uniquindio.application.repositories.CommentRepository;
import co.edu.uniquindio.application.repositories.PlaceRepository;
import co.edu.uniquindio.application.repositories.UserRepository;
import co.edu.uniquindio.application.services.PlaceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import co.edu.uniquindio.application.services.GeoUtils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {

    private final PlaceMapper placeMapper;
    private final ShowPlaceMapper showPlaceMapper;
    private final PlaceRepository placeRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final StatsMapper statsMapper;
    private final PlaceDetailMapper placeDetailMapper;


    @Override
    public void create(String id, CreatePlaceDTO createPlaceDTO) throws Exception {

        Optional<User> optionalUser = userRepository.findById(id);

        if(optionalUser.isEmpty()){
            throw new ResourceNotFoundException("usuario no encontrado");
        }

        if(verifyExistence(createPlaceDTO)){
            throw new ValueConflictException("este alojamiento ya existe");
        }
        Place place = placeMapper.toEntity(createPlaceDTO);
        place.setUser(optionalUser.get());
        placeRepository.save(place);

    }

    private boolean verifyExistence(CreatePlaceDTO createPlaceDTO) {

        for (Place place : placeRepository.findByState(State.ACTIVE)) {
            double distancia = GeoUtils.calcularDistanciaUbi(
                    createPlaceDTO.latitude(), createPlaceDTO.longitude(),
                    place.getLocation().getCoordinates().getLatitude(),
                    place.getLocation().getCoordinates().getLongitude()
            );

            if (distancia <= 5 && place.getTitle().equalsIgnoreCase(createPlaceDTO.title())) {
                return true;
            }
        }
        return false;
    }

    //update
    @Override
    public void edit(String id, EditPlaceDTO editPlaceDTO) throws Exception {

        Optional<Place> place = placeRepository.findById(id);
        if(place.isEmpty()){
            throw new ResourceNotFoundException("Place not found");
        }
        placeMapper.editPlaceFromDto(editPlaceDTO, place.get());

        placeRepository.save(place.get());
    }


    @Override
    public void delete(String id) throws Exception {
        Optional<Place> place = placeRepository.findById(id);
        if(place.isEmpty()){
            throw new ResourceNotFoundException("No se encontró el alojamiento");
        }
        Optional<Booking> booking = bookingRepository.findByPlaceIdAndBookingState(id, BookingState.PENDING);
        if(booking.isPresent()){
            throw new UnauthorizedException("no puedes eliminar este alojamiento, tiene reservas pendientes");
        }

        boolean hasFutureActive = bookingRepository.existsByPlace_IdAndCheckInAfterAndBookingStateIn(
                id, LocalDateTime.now(), List.of(BookingState.PENDING, BookingState.CONFIRMED));
        if (hasFutureActive) throw new UnauthorizedException("Tiene reservas futuras activas");

        place.get().setState(State.INACTIVE);
        placeRepository.save(place.get());
    }

    @Override
    public List<PlaceDTO> search(ListPlaceDTO listPlaceDTO, int page) throws Exception {

        if(listPlaceDTO.getMinimum() != null && listPlaceDTO.getMaximum() != null && listPlaceDTO.getMinimum() > listPlaceDTO.getMaximum()){
            throw new BadRequestException("el precio minimo no debe superar el precio maximo");
        }

        if(listPlaceDTO.getList() == null){
            listPlaceDTO.setList(new ArrayList<>());
        }

        Pageable pageable = PageRequest.of(page, 10);
        Page<Place> places = placeRepository.searchPlaces(listPlaceDTO, pageable);

        if(places.isEmpty()){
            throw new ResourceNotFoundException("No hay alojamientos disponibles, prueba otro filtro");
        }
        return places.stream()
                .map(showPlaceMapper::toPlaceDTO)
                .toList();
    }


    @Override
    public List<Services> listAllServices(String id) throws Exception {

        Optional<Place> place = placeRepository.findById(id);
        if(place.isPresent()){
            return place.get().getAmenities();
        }
        throw new ResourceNotFoundException("No se encontraron servicios del alojamiento");
    }

    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public PlaceStatsDTO stats(String placeId, LocalDateTime from, LocalDateTime to) throws Exception {
        // valida que el place exista (si no lo haces ya en otro método)
        var place = placeRepository.findById(placeId)
                .orElseThrow(() -> new co.edu.uniquindio.application.exceptions.ResourceNotFoundException("No existe el alojamiento"));

        long reservations = bookingRepository.countByPlaceIdBetween(placeId, from, to);
        Double avg = commentRepository.avgRatingByPlaceIdBetween(placeId, from, to);
        double averageRating = (avg == null) ? 0.0 : avg;

        return new PlaceStatsDTO(reservations, averageRating);
    }

    @Override
    public List<PlaceDTO> listAllPlacesHost(String id, int page) throws Exception {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Place> places = placeRepository.getPlaces(id, pageable);

        return places.toList().stream().map(showPlaceMapper::toPlaceDTO).collect(Collectors.toList());
    }

    @Override
    public PlaceDetailDTO get(String id) throws Exception {
        Optional<Place> place = placeRepository.findById(id);
        if(place.isEmpty()){
            throw new ResourceNotFoundException("no se encontró el alojamiento");
        }

        return placeDetailMapper.toPlaceDetailDTO(place.get());
    }

    @Override
    @Transactional
    public Place setImages(String placeId, List<String> urls) throws Exception {
        if (urls == null || urls.isEmpty() || urls.size() > 10) {
            throw new BadRequestException("Debe enviar entre 1 y 10 URLs de imágenes");
        }
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new ResourceNotFoundException("No existe el alojamiento"));

        // guardamos las URLs; la principal es urls.get(0)
        place.setPics_url(urls);
        return placeRepository.save(place);
    }



}