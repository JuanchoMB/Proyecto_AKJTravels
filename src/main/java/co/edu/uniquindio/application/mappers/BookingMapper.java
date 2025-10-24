package co.edu.uniquindio.application.mappers;

import co.edu.uniquindio.application.dto.bookingDTO.BookingDTO;
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
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)public interface BookingMapper {

    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID().toString())")
    @Mapping(target = "bookingState", constant = "COMPLETED")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(source = "place", target = "place")
    @Mapping(source = "user", target = "user")

    Booking toEntity(CreateBookingDTO createBookingDTO, Place place, User user);
    BookingDTO toBookingDTO(Booking booking);

}