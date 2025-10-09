package co.edu.uniquindio.application.mappers;

import co.edu.uniquindio.application.dto.placeDTO.CreatePlaceDTO;
import co.edu.uniquindio.application.dto.placeDTO.EditPlaceDTO;
import co.edu.uniquindio.application.model.Place;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PlaceMapper {

    // CreatePlaceDTO -> Place (creación)
    // NOTA: user se setea en el servicio; por eso va ignore
    @Mapping(target = "location.country", source = "country")
    @Mapping(target = "location.department", source = "department")
    @Mapping(target = "location.city", source = "city")
    @Mapping(target = "location.neighborhood", source = "neighborhood")
    @Mapping(target = "location.street", source = "street")
    @Mapping(target = "location.postalCode", source = "postalCode")
    @Mapping(target = "location.coordinates.latitude", source = "latitude")
    @Mapping(target = "location.coordinates.longitude", source = "longitude")
    @Mapping(target = "pics_url", source = "picsUrl")                // <— Place usa snake_case
    @Mapping(target = "placeType", source = "placeType")
    @Mapping(target = "state", constant = "ACTIVE")
    @Mapping(target = "totalRatings", constant = "0")
    @Mapping(target = "averageRatings", constant = "0")
    @Mapping(target = "comments", expression = "java(new java.util.ArrayList<>())")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "user", ignore = true)
    Place toEntity(CreatePlaceDTO dto);

    // Place -> CreatePlaceDTO (solo si lo usas; mapeo explícito de anidados)
    @Mapping(target = "country", source = "location.country")
    @Mapping(target = "department", source = "location.department")
    @Mapping(target = "city", source = "location.city")
    @Mapping(target = "neighborhood", source = "location.neighborhood")
    @Mapping(target = "street", source = "location.street")
    @Mapping(target = "postalCode", source = "location.postalCode")
    @Mapping(target = "latitude", source = "location.coordinates.latitude")
    @Mapping(target = "longitude", source = "location.coordinates.longitude")
    @Mapping(target = "picsUrl", source = "pics_url")                // <— inverso
    @Mapping(target = "placeType", source = "placeType")
    CreatePlaceDTO toCreatePlaceDTO(Place place);

    // EditPlaceDTO -> Place (actualización parcial)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "location.country", source = "country")
    @Mapping(target = "location.department", source = "department")
    @Mapping(target = "location.city", source = "city")
    @Mapping(target = "location.neighborhood", source = "neighborhood")
    @Mapping(target = "location.street", source = "street")
    @Mapping(target = "location.postalCode", source = "postalCode")
    @Mapping(target = "pics_url", source = "pics_url")               // Edit usa snake_case
    @Mapping(target = "placeType", source = "placeType")     // Edit usa placeType
    // Ignora campos inmutables / calculados
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "totalRatings", ignore = true)
    @Mapping(target = "averageRatings", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "comments", ignore = true)
    void EditPlaceFromDto(EditPlaceDTO dto, @MappingTarget Place place);
}
