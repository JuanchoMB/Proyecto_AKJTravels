// co/edu/uniquindio/application/mappers/BookingMapper.java
package co.edu.uniquindio.application.mappers;

import co.edu.uniquindio.application.dto.bookingDTO.CreateBookingDTO;
import co.edu.uniquindio.application.dto.bookingDTO.BookingDTO;
import co.edu.uniquindio.application.model.Booking;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookingMapper {

    @Mappings({
            @Mapping(target = "id",        expression = "java(UUID.randomUUID().toString())"),
            @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())"),
            @Mapping(target = "bookingState", constant = "PENDING"),
            // lo setea el servicio
            @Mapping(target = "user", ignore = true),
            @Mapping(target = "place", ignore = true)
    })
    Booking toEntity(CreateBookingDTO dto);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "placeId", source = "place.id"),
            @Mapping(target = "userId", source = "user.id"),
            @Mapping(target = "checkIn", source = "checkIn"),
            @Mapping(target = "checkOut", source = "checkOut"),
            @Mapping(target = "guestNumber", source = "guestNumber"),
            @Mapping(target = "bookingState", source = "bookingState"),
            @Mapping(target = "createdAt", source = "createdAt")
    })
    BookingDTO toBookingDTO(Booking booking);
}
