package co.edu.uniquindio.application.mappers;

import co.edu.uniquindio.application.dto.userDTO.CreateUserDTO;
import co.edu.uniquindio.application.dto.userDTO.EditUserDTO;
import co.edu.uniquindio.application.dto.userDTO.UserDTO;
import co.edu.uniquindio.application.model.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    // CreateUserDTO -> User
    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID().toString())")
    @Mapping(target = "state", constant = "ACTIVE")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "isHost", constant = "false")
    // description se mapea automáticamente si el nombre coincide en DTO y entidad
    User toEntity(CreateUserDTO dto);

    // User -> UserDTO (ojo con BirthDate en DTO vs birthDate en entidad)
    @Mapping(target = "BirthDate", source = "birthDate")
    UserDTO toUserDTO(User user);

    // EditUserDTO -> User (patch: ignora nulls y campos inmutables)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "country", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "isHost", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "birthDate", source = "BirthDate")
    // description YA NO se ignora: se mapea por nombre si viene en el DTO
    void editUserFromDto(EditUserDTO dto, @MappingTarget User user);
}
