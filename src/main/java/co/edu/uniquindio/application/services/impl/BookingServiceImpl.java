package co.edu.uniquindio.application.services.impl;

import co.edu.uniquindio.application.dto.bookingDTO.BookingDTO;
import co.edu.uniquindio.application.dto.bookingDTO.BookingListItemDTO;
import co.edu.uniquindio.application.dto.bookingDTO.CreateBookingDTO;
import co.edu.uniquindio.application.dto.bookingDTO.SearchBookingDTO;
import co.edu.uniquindio.application.dto.bookingDTO.UserBookingDTO;
import co.edu.uniquindio.application.dto.externalServiceDTO.SendEmailDTO;
import co.edu.uniquindio.application.exceptions.BadRequestException;
import co.edu.uniquindio.application.exceptions.ForbiddenException;
import co.edu.uniquindio.application.exceptions.ResourceNotFoundException;
import co.edu.uniquindio.application.exceptions.UnauthorizedException;
import co.edu.uniquindio.application.exceptions.ValueConflictException;
import co.edu.uniquindio.application.mappers.BookingMapper;
import co.edu.uniquindio.application.model.Booking;
import co.edu.uniquindio.application.model.Place;
import co.edu.uniquindio.application.model.User;
import co.edu.uniquindio.application.model.enums.BookingState;
import co.edu.uniquindio.application.model.enums.State;
import co.edu.uniquindio.application.repositories.BookingRepository;
import co.edu.uniquindio.application.repositories.PlaceRepository;
import co.edu.uniquindio.application.repositories.UserRepository;
import co.edu.uniquindio.application.repositories.spec.BookingSpecifications;
import co.edu.uniquindio.application.services.BookingService;
import co.edu.uniquindio.application.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingMapper bookingMapper;
    private final BookingRepository bookingRepository;
    private final PlaceRepository placeRepository;
    private final UserRepository userRepository;
    private final CurrentUserServiceImpl currentUserService;
    private final EmailService emailService;

    @Override
    public void create(String id, String userId, CreateBookingDTO createBookingDTO) throws Exception {

        if (!createBookingDTO.checkIn().plusDays(1).isBefore(createBookingDTO.checkOut())
                && !createBookingDTO.checkIn().plusDays(1).isEqual(createBookingDTO.checkOut())) {
            throw new BadRequestException("La reserva debe ser mínimo de 1 noche");
        }

        if (createBookingDTO.checkIn().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("el checkIn es invalido");
        }

        if (createBookingDTO.checkIn().isAfter(createBookingDTO.checkOut())) {
            throw new BadRequestException("Datos incorrectos o la fecha de checkIn está despues de la fecha de check Out");
        }

        boolean solapa = bookingRepository.existsOverlappingBooking(id, createBookingDTO.checkIn(), createBookingDTO.checkOut());
        if (solapa) {
            throw new ValueConflictException("fechas no disponibles");
        }

        Place place = placeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe el alojamiento"));

        if (place.getState() != State.ACTIVE) {
            throw new BadRequestException("El alojamiento no está activo");
        }

        // Validación de capacidad
        Integer guests = createBookingDTO.guest_number();
        if (guests > place.getCapacity()) {
            throw new BadRequestException("Excede capacidad del lugar");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("No existe el usuario"));

        Booking booking = bookingMapper.toEntity(createBookingDTO, place, user);
        if (booking.getBookingState() == null) {
            booking.setBookingState(BookingState.PENDING);
        }

        bookingRepository.save(booking);
    }

    @Override
    public void delete(String id) throws Exception {
        Optional<Booking> bookingOpt = bookingRepository.findById(id);
        if (bookingOpt.isEmpty()) {
            throw new ResourceNotFoundException("No existe esta reserva");
        }

        Booking booking = bookingOpt.get();

        // Solo el dueño de la reserva puede cancelarla
        if (!Objects.equals(currentUserService.getCurrentUser(), booking.getUser().getId())) {
            throw new ForbiddenException("No te pertenece esta reserva");
        }

        // Solo se pueden cancelar PENDING o CONFIRMED
        if (booking.getBookingState() == BookingState.PENDING
                || booking.getBookingState() == BookingState.CONFIRMED) {

            LocalDateTime checkIn = booking.getCheckIn();
            LocalDateTime now = LocalDateTime.now();

            // Regla: solo se puede cancelar si faltan al menos 48 horas
            if (now.isBefore(checkIn.minusHours(48))) {
                booking.setBookingState(BookingState.CANCELED);
                bookingRepository.save(booking);

                // Notificar al anfitrión
                try {
                    User host = booking.getPlace().getUser();
                    String hostEmail = host.getEmail();

                    String subject = "Reserva cancelada en AKJTravel";
                    String body = """
                            Hola %s,

                            El usuario %s ha cancelado una reserva para tu alojamiento "%s".

                            Fechas:
                            - Check-in: %s
                            - Check-out: %s
                            - Huéspedes: %d

                            Estado actual: CANCELADA.

                            """.formatted(
                            host.getName(),
                            booking.getUser().getName(),
                            booking.getPlace().getTitle(),
                            booking.getCheckIn(),
                            booking.getCheckOut(),
                            booking.getGuest_number()
                    );

                    emailService.sendMail(new SendEmailDTO(hostEmail, subject, body));
                } catch (Exception e) {
                    // No romper la cancelación si el correo falla
                    e.printStackTrace();
                }

            } else {
                throw new ValueConflictException("solo puedes cancelar una reserva 48 horas antes de la fecha de check in");
            }
        } else {
            throw new UnauthorizedException("no puedes cancelar esta reserva");
        }
    }

    @Override
    public List<BookingDTO> listBookings(String id, int page, SearchBookingDTO searchBookingDTO) throws Exception {

        Optional<Place> place = placeRepository.findById(id);

        if (searchBookingDTO.guest_number() != null && searchBookingDTO.guest_number() <= 0) {
            throw new BadRequestException("el numero de huespedes no puede ser menor o 0");
        }
        return getBookingPlaceDTOS(id, page, searchBookingDTO, place.isEmpty(), place);
    }

    @Override
    public List<BookingDTO> listBookingsUser(String id, int page, SearchBookingDTO searchBookingDTO) throws Exception {

        Optional<User> user = userRepository.findById(id);
        return getBookingUserDTOS(id, page, searchBookingDTO, user.isEmpty(), user);
    }

    @Override
    public void confirm(String bookingId) throws Exception {
        var b = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("No existe esta reserva"));
        // dueño del alojamiento
        String ownerId = b.getPlace().getUser().getId();
        if (!Objects.equals(ownerId, currentUserService.getCurrentUser())) {
            throw new ForbiddenException("No eres el anfitrión de este alojamiento");
        }
        if (b.getBookingState() != BookingState.PENDING) {
            throw new BadRequestException("Solo se puede confirmar si está PENDING");
        }
        b.setBookingState(BookingState.CONFIRMED);
        bookingRepository.save(b);
    }

    @NotNull
    private List<BookingDTO> getBookingUserDTOS(String id, int page, SearchBookingDTO searchBookingDTO, boolean empty, Optional<User> user) {
        if (empty) {
            throw new ResourceNotFoundException("No existe el usuario");
        }

        Pageable pageable = PageRequest.of(page, 10);
        Page<Booking> bookings = bookingRepository.findBookingsByUserWithFilters(id, searchBookingDTO, pageable);

        return bookings.stream()
                .map(bookingMapper::toBookingDTO)
                .toList();
    }

    @NotNull
    private List<BookingDTO> getBookingPlaceDTOS(String id, int page, SearchBookingDTO searchBookingDTO, boolean empty, Optional<Place> place) {
        if (empty) {
            throw new ResourceNotFoundException("No existe el alojamiento");
        }

        Pageable pageable = PageRequest.of(page, 10);
        Page<Booking> bookings = bookingRepository.findBookingsByPlaceWithFilters(id, searchBookingDTO, pageable);

        return bookings.stream()
                .map(bookingMapper::toBookingDTO)
                .toList();
    }

    @Override
    public List<BookingListItemDTO> listByPlace(String placeId,
                                                BookingState state,
                                                LocalDateTime from,
                                                LocalDateTime to,
                                                Integer guests) throws Exception {
        Specification<Booking> spec = Specification.allOf(
                BookingSpecifications.byPlaceId(placeId),
                BookingSpecifications.withState(state),
                BookingSpecifications.fromDate(from),
                BookingSpecifications.toDate(to),
                BookingSpecifications.withGuests(guests)
        );

        return bookingRepository.findAll(spec)
                .stream()
                .map(bookingMapper::toBookingListItemDTO)
                .toList();
    }

    @Override
    public List<UserBookingDTO> listUserBookings(String userId) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("No existe el usuario"));

        List<Booking> bookings = bookingRepository.findByUser(user);

        return bookings.stream()
                .map(bookingMapper::toUserBookingDTO)
                .toList();
    }
}
