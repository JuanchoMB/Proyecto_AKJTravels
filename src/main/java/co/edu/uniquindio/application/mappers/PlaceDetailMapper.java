package co.edu.uniquindio.application.mappers;

import co.edu.uniquindio.application.dto.placeDTO.PlaceDetailDTO;
import co.edu.uniquindio.application.dto.userDTO.UserDetailDTO;
import co.edu.uniquindio.application.model.Place;
import co.edu.uniquindio.application.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = { UserMapper.class })
public interface PlaceDetailMapper {

    @Mapping(source = "id",                        target = "id")
    @Mapping(source = "location.coordinates.latitude",  target = "latitude")
    @Mapping(source = "location.coordinates.longitude", target = "longitude")
    @Mapping(source = "price",                     target = "price")
    @Mapping(source = "pics_url",                  target = "pics_url")
    @Mapping(source = "description",               target = "description")
    @Mapping(source = "amenities",                 target = "services")
    @Mapping(source = "title",                     target = "title")
    @Mapping(source = "capacity",                  target = "capacity")
    @Mapping(source = "averageRatings",            target = "averageRatings")
    @Mapping(source = "user",                      target = "userDetailDTO")
    @Mapping(source = "location.street",           target = "street")
    @Mapping(source = "location.neighborhood",     target = "neighborhood")
    @Mapping(source = "location.city",             target = "city")
    @Mapping(source = "location.department",       target = "department")
    @Mapping(source = "location.country",          target = "country")
    @Mapping(source = "location.postalCode",       target = "postalCode")
    PlaceDetailDTO toPlaceDetailDTO(Place place);

    List<PlaceDetailDTO> toPlaceDetailDTO(List<Place> places);
}

