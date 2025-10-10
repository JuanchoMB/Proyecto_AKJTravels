package co.edu.uniquindio.application.mappers;


import co.edu.uniquindio.application.dto.placeDTO.PlaceDTO;
import co.edu.uniquindio.application.model.Place;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ShowPlaceMapper {

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "title", source = "title"),
            @Mapping(target = "city", source = "location.city"),
            @Mapping(target = "price", source = "price"),
            @Mapping(target = "averageRatings", source = "averageRatings"),
            @Mapping(target = "picsUrl", source = "picsUrl")
    })
    PlaceDTO toPlaceDTO(Place place);


    // falta el average rating +++



}