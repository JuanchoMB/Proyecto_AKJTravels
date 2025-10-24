package co.edu.uniquindio.application.mappers;

import co.edu.uniquindio.application.dto.placeDTO.PlaceDTO;
import co.edu.uniquindio.application.model.Place;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)public interface ShowPlaceMapper {

    @Mapping(target = "photo_url", expression = "java(place.getPics_url() != null && !place.getPics_url().isEmpty() ? place.getPics_url().get(0) : null)")
    @Mapping(target = "city", source = "place.location.city")
    @Mapping(target = "average_rating", source = "averageRatings")
    PlaceDTO toPlaceDTO(Place place);

}