package co.edu.uniquindio.application.mappers;

import co.edu.uniquindio.application.dto.userDTO.CreateUserDTO;
import co.edu.uniquindio.application.dto.userDTO.EditUserDTO;
import co.edu.uniquindio.application.dto.userDTO.UserDTO;
import co.edu.uniquindio.application.model.User;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE // no moleste por campos que no existan
)
public interface UserMapper {

    // Crea la entidad a partir del DTO (solo mapea campos con el mismo nombre)
    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID().toString())")
    @Mapping(target = "status", constant = "ACTIVE")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "role", constant = "GUEST")
    User toEntity(CreateUserDTO dto);

    // Entidad -> DTO (deja que MapStruct mapee los que coinciden por nombre)
    UserDTO toUserDTO(User user);

    // Actualización parcial (ignora nulls del DTO)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDto(EditUserDTO dto, @MappingTarget User user);
}
