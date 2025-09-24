package co.edu.uniquindio.application.mappers;

import co.edu.uniquindio.application.dto.accommodationDTO.AccommodationDTO;
import co.edu.uniquindio.application.dto.accommodationDTO.CreateAccommodationDTO;
import co.edu.uniquindio.application.model.Accommodation;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface AccommodationMapper {

    @BeanMapping(ignoreByDefault = true)
    Accommodation toEntity(CreateAccommodationDTO dto);

    @BeanMapping(ignoreByDefault = true)
    AccommodationDTO toDTO(Accommodation entity);
}
