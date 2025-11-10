package co.edu.uniquindio.application.mappers;

import co.edu.uniquindio.application.dto.bookingDTO.BookingDTO;
import co.edu.uniquindio.application.dto.bookingDTO.BookingListItemDTO;
import co.edu.uniquindio.application.dto.bookingDTO.CreateBookingDTO;
import co.edu.uniquindio.application.model.Place;
import co.edu.uniquindio.application.model.Booking;
import co.edu.uniquindio.application.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = { UserMapper.class } // para mapear User -> UserDTO
)
public interface BookingMapper {

    // 1) Listado para el dashboard: SIN anotaciones extra, mapea por nombre
    BookingListItemDTO toBookingListItemDTO(Booking booking);

    // 2) Detalle/uso general
    BookingDTO toBookingDTO(Booking booking);

    // 3) Creación: AQUÍ sí van las anotaciones
    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID().toString())")
    @Mapping(target = "bookingState", constant = "PENDING") // o tu estado por defecto
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(source = "place", target = "place")
    @Mapping(source = "user", target = "user")
    Booking toEntity(CreateBookingDTO createBookingDTO, Place place, User user);
}