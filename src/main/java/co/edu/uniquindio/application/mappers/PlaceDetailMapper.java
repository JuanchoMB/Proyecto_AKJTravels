package co.edu.uniquindio.application.mappers;

import co.edu.uniquindio.application.dto.placeDTO.PlaceDetailDTO;
import co.edu.uniquindio.application.dto.userDTO.UserDetailDTO;
import co.edu.uniquindio.application.model.Place;
import co.edu.uniquindio.application.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PlaceDetailMapper {

    @Mapping(source = "user", target = "userDetailDTO")
    @Mapping(source = "location.coordinates.latitude", target = "latitude")
    @Mapping(source = "location.coordinates.longitude", target = "longitude")

    PlaceDetailDTO toPlaceDetailDTO(Place place);

    UserDetailDTO toUserDetailDTO(User user);

}