package co.edu.uniquindio.application.mappers;

import co.edu.uniquindio.application.dto.placeDTO.PlaceDTO;
import co.edu.uniquindio.application.dto.placeDTO.PlaceDetailDTO;
import co.edu.uniquindio.application.model.Place;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ShowPlaceMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "photo_url",
            expression = "java(place.getPics_url() != null && !place.getPics_url().isEmpty() ? place.getPics_url().get(0) : null)")
    @Mapping(target = "average_rating", source = "averageRatings")
    @Mapping(target = "city", source = "location.city")
    PlaceDTO toPlaceDTO(Place place);

    List<PlaceDTO> toPlaceDTO(List<Place> places);
}