// co/edu/uniquindio/application/mappers/UserMapper.java
package co.edu.uniquindio.application.mappers;

import co.edu.uniquindio.application.dto.userDTO.CreateUserDTO;
import co.edu.uniquindio.application.dto.userDTO.EditUserDTO;
import co.edu.uniquindio.application.dto.userDTO.UserDTO;
import co.edu.uniquindio.application.model.User;
import co.edu.uniquindio.application.model.enums.State;
import org.mapstruct.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mappings({
            @Mapping(target = "id",        expression = "java(UUID.randomUUID().toString())"),
            @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())"),
            @Mapping(target = "state",     constant = "ACTIVE"),
            @Mapping(target = "isHost",    constant = "false")
    })
    User toEntity(CreateUserDTO dto);

    @BeanMapping(ignoreByDefault = true)
    @Mappings({
            @Mapping(target = "name",      source = "name"),
            @Mapping(target = "phone",     source = "phone"),
            @Mapping(target = "photoUrl",  source = "photoUrl"),
            @Mapping(target = "birthDate", source = "birthDate"),
            @Mapping(target = "description", source = "description")
    })
    void editUserFromDto(EditUserDTO dto, @MappingTarget User user);

    @Mappings({
            @Mapping(target = "id",       source = "id"),
            @Mapping(target = "name",     source = "name"),
            @Mapping(target = "email",    source = "email"),
            @Mapping(target = "phone",    source = "phone"),
            @Mapping(target = "photoUrl", source = "photoUrl"),
            @Mapping(target = "role",     source = "role")
    })
    UserDTO toUserDTO(User user);
}
