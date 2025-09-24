package co.edu.uniquindio.application.mappers;

import co.edu.uniquindio.application.dto.accommodationDTO.AccommodationDTO;
import co.edu.uniquindio.application.model.Accommodation;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface ShowAccommodationMapper {

    @BeanMapping(ignoreByDefault = true)
    AccommodationDTO show(Accommodation entity);
}
