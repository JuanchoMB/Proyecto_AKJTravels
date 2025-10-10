// co/edu/uniquindio/application/mappers/PlaceMapper.java
package co.edu.uniquindio.application.mappers;

import co.edu.uniquindio.application.dto.placeDTO.CreatePlaceDTO;
import co.edu.uniquindio.application.dto.placeDTO.EditPlaceDTO;
import co.edu.uniquindio.application.model.Place;
import org.mapstruct.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PlaceMapper {

    @Mappings({
            @Mapping(target = "id", expression = "java(UUID.randomUUID().toString())"),
            @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())"),
            @Mapping(target = "picsUrl", source = "picsUrl"), // OJO
            @Mapping(target = "location.country",      source = "country"),
            @Mapping(target = "location.department",   source = "department"),
            @Mapping(target = "location.city",         source = "city"),
            @Mapping(target = "location.neighborhood", source = "neighborhood"),
            @Mapping(target = "location.street",       source = "street"),
            @Mapping(target = "location.postalCode",   source = "postalCode"),
            @Mapping(target = "location.coordinates.latitude",  source = "latitude"),
            @Mapping(target = "location.coordinates.longitude", source = "longitude"),
            // set por servicio
            @Mapping(target = "user", ignore = true),
            @Mapping(target = "state", ignore = true),
            @Mapping(target = "totalRatings", ignore = true),
            @Mapping(target = "averageRatings", ignore = true)
    })
    Place toEntity(CreatePlaceDTO dto);

    @BeanMapping(ignoreByDefault = true)
    @Mappings({
            @Mapping(target = "title", source = "title"),
            @Mapping(target = "description", source = "description"),
            @Mapping(target = "capacity", source = "capacity"),
            @Mapping(target = "price", source = "price"),
            @Mapping(target = "placeType", source = "placeType"),
            @Mapping(target = "amenities", source = "amenities"),
            @Mapping(target = "picsUrl", source = "picsUrl"),
            @Mapping(target = "location.country",      source = "country"),
            @Mapping(target = "location.department",   source = "department"),
            @Mapping(target = "location.city",         source = "city"),
            @Mapping(target = "location.neighborhood", source = "neighborhood"),
            @Mapping(target = "location.street",       source = "street"),
            @Mapping(target = "location.postalCode",   source = "postalCode"),
            @Mapping(target = "location.coordinates.latitude",  source = "latitude"),
            @Mapping(target = "location.coordinates.longitude", source = "longitude")
    })
    void editPlaceFromDto(EditPlaceDTO dto, @MappingTarget Place place);
}
