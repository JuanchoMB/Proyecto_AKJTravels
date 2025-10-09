package co.edu.uniquindio.application.mappers;

import co.edu.uniquindio.application.dto.bookingDTO.BookingDTO;
import co.edu.uniquindio.application.dto.bookingDTO.CreateBookingDTO;
import co.edu.uniquindio.application.model.Booking;
import co.edu.uniquindio.application.model.Place;
import co.edu.uniquindio.application.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {UserMapper.class})
public interface BookingMapper {

    // CreateBookingDTO + refs -> Booking
    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID().toString())")
    @Mapping(target = "bookingState", constant = "PENDING")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(source = "place", target = "place")
    @Mapping(source = "user", target = "user")
    Booking toEntity(CreateBookingDTO dto, Place place, User user);

    // Booking -> BookingDTO
    // title sale del Place, state es el State del Place
    @Mapping(target = "title", source = "place.title")
    @Mapping(target = "state", source = "place.state")
    @Mapping(target = "user", source = "user") // usa UserMapper (incluye BirthDate)
    BookingDTO toBookingDTO(Booking booking);
}
