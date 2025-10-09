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
import co.edu.uniquindio.application.model.enums.Amenities;
import co.edu.uniquindio.application.model.enums.BookingState;
import co.edu.uniquindio.application.model.enums.State;
import co.edu.uniquindio.application.repositories.BookingRepository;
import co.edu.uniquindio.application.repositories.CommentRepository;
import co.edu.uniquindio.application.repositories.PlaceRepository;
import co.edu.uniquindio.application.repositories.UserRepository;
import co.edu.uniquindio.application.services.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import co.edu.uniquindio.application.services.GeoUtils;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {

    private final PlaceMapper placeMapper;
    private final Map<String, Place> placeStore = new ConcurrentHashMap<>();
    private final ShowPlaceMapper showPlaceMapper;
    private final PlaceRepository placeRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
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
            double distancia = GeoUtils.calcularDistancia(
                    createPlaceDTO.latitude(), createPlaceDTO.longitude(),
                    place.getLocation().getCoordinates().getLatitude(),
                    place.getLocation().getCoordinates().getLongitude()
            );

            if (distancia <= 5 && place.getTitle().equalsIgnoreCase(createPlaceDTO.title())) { // 10 metros de umbral
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
        placeMapper.EditPlaceFromDto(editPlaceDTO, place.get());

        placeRepository.save(place.get());
    }


    //eliminar un alojamiento que no tenga reservas
    @Override
    public void delete(String id) throws Exception {
        Optional<Place> place = placeRepository.findById(id);
        if(place.isEmpty()){
            throw new ResourceNotFoundException("No se encontró el alojamiento");
        }
        //me trae todas las reservas del alojamiento cuyo estado sea pendiente
        Optional<Booking> booking = bookingRepository.findByPlaceIdAndBookingState(id, BookingState.PENDING);
        if(booking.isPresent()){
            throw new UnauthorizedException("no puedes eliminar este alojamiento, tiene reservas pendientes");
        }
        place.get().setState(State.INACTIVE);
        placeRepository.save(place.get());
    }


    /* ya está en bookingService pero lo dejo comentado (pendiente a revisar)
    //Es para listar las reservas de un alojamiento? si es así deben enviar el id del alojamiento no una lista
    @Override
    public List<BookingDTO> listAll(ListBookingsDTO listBookingsDTO) throws Exception {


        return List.of();
    }*/

    // filtro de busqueda de los alojamientos (rango minimo - maximo precio y servicios added )
    @Override
    public List<PlaceDTO> search(ListPlaceDTO listPlaceDTO, int page) throws Exception {

        if(listPlaceDTO.minimum() != null && listPlaceDTO.maximum() != null && listPlaceDTO.minimum() > listPlaceDTO.maximum()){
            throw new BadRequestException("el precio minimo no debe superar el precio maximo");
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


    // devuelve la lista de todos los servicios del alojamiento
    @Override
    public List<Amenities> listAllAmenities(String id) throws Exception {

        Optional<Place> place = placeRepository.findById(id);
        if(place.isPresent()){
            return place.get().getAmenities();
        }
        // o con este se resume más: .orElseThrow(() -> new ResourceNotFoundException("No se encontraron servicios del alojamiento"));
        throw new ResourceNotFoundException("No se encontraron servicios del alojamiento");
    }

    //stats del alojamiento, se puede aplicar rango de fechas
    @Override
    public PlaceStatsDTO stats(String id, StatsDateDTO statsDateDTO) throws Exception {

        double averageRating = commentRepository.findAverageRatingByPlaceId(id, statsDateDTO.startDate(), statsDateDTO.endDate());
        long totalComments = commentRepository.countByPlaceId(id, statsDateDTO.startDate(), statsDateDTO.endDate());
        long totalReservations = bookingRepository.countByPlaceIdAndBetween(id, statsDateDTO.startDate(), statsDateDTO.endDate());
        double occupancy = bookingRepository.findAverageOccupancyByPlaceId(id, statsDateDTO.startDate(), statsDateDTO.endDate());
        long totalDays = (statsDateDTO.startDate() != null && statsDateDTO.endDate() != null)
                ? ChronoUnit.DAYS.between(statsDateDTO.startDate(), statsDateDTO.endDate())
                : 30; // se calcula el promedio de 30 dias si el usuario no pasa fechas

        double occupancyRate = totalDays > 0 ? (occupancy / totalDays) * 100 : 0.0;
        int cancellations = bookingRepository.countCancellationsByPlaceId(id, statsDateDTO.startDate(), statsDateDTO.endDate());
        double totalRevenue = bookingRepository.findAverageRevenueByPlaceId(id, statsDateDTO.startDate(), statsDateDTO.endDate());

        return statsMapper.toPlaceStatsDTO(averageRating, totalComments, totalReservations, occupancyRate, cancellations, totalRevenue);
    }

    //Corregido (pendiente), se debe crear una consulta que dado el id del host, se traiga sus alojamientos.
    @Override
    public List<PlaceDTO> listAllPlacesHost(String id, int page) throws Exception {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Place> places = placeRepository.getPlaces(id, pageable);

        return places.toList().stream().map(showPlaceMapper::toPlaceDTO).collect(Collectors.toList());
    }

    // para ver el alojamiento
    @Override
    public PlaceDetailDTO get(String id) throws Exception {
        Optional<Place> place = placeRepository.findById(id);
        if(place.isEmpty()){
            throw new ResourceNotFoundException("no se encontró el alojamiento");
        }

        return placeDetailMapper.toPlaceDetailDTO(place.get());
    }
}
