package co.edu.uniquindio.application.mappers;

import co.edu.uniquindio.application.dto.CreateUserDTO;
import co.edu.uniquindio.application.dto.UserDTO;
import co.edu.uniquindio.application.model.UserStatus;
import co.edu.uniquindio.application.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.time.LocalDateTime;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        imports = {LocalDateTime.class, UserStatus.class}
)
public interface UserMapper {

    // PARA CREAR: no seteamos id ni passwordHash aquí (lo hace el servicio)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "role", expression = "java(co.edu.uniquindio.application.model.Role.USER)")
    @Mapping(target = "status", expression = "java(UserStatus.ACTIVE)")
    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    // Si NO tienes photoUrl en la entidad, ignóralo:
    // @Mapping(target = "photoUrl", ignore = true)
    User



    toEntity(CreateUserDTO userDTO);

    // Hacia DTO
    UserDTO toUserDTO(User user);
}
