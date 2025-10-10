package co.edu.uniquindio.application.mappers;

import co.edu.uniquindio.application.dto.placeDTO.PlaceDetailDTO;
import co.edu.uniquindio.application.dto.userDTO.UserDetailDTO;
import co.edu.uniquindio.application.model.Place;
import co.edu.uniquindio.application.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PlaceDetailMapper {

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "name", source = "title"),
            @Mapping(target = "photoUrl", source = "picsUrl"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "ownerId", source = "user.id"),
            @Mapping(target = "ownerName", source = "user.name"),
            @Mapping(target = "lat", source = "location.coordinates.latitude"),
            @Mapping(target = "lng", source = "location.coordinates.longitude")
    })
    PlaceDetailDTO toPlaceDetailDTO(Place place);

    UserDetailDTO toUserDetailDTO(User user);

}