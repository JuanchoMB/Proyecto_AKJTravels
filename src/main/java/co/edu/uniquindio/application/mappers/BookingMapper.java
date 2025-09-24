package co.edu.uniquindio.application.mappers;

import co.edu.uniquindio.application.dto.bookingDTO.BookingDTO;
import co.edu.uniquindio.application.dto.bookingDTO.CreateBookingDTO;
import co.edu.uniquindio.application.model.Booking;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface BookingMapper {

    // No mapear campos hasta que la entidad Booking esté completa
    @BeanMapping(ignoreByDefault = true)
    Booking toEntity(CreateBookingDTO createBookingDTO);

    // Evitar mapeo a DTO por ahora (si alguien lo llama, retorna null para no compilar MapStruct)
    default BookingDTO toBookingDTO(Booking booking) {
        return null;
    }
}
