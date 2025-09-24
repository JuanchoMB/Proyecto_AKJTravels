package co.edu.uniquindio.application.mappers;

import co.edu.uniquindio.application.dto.userDTO.CreateUserDTO;
import co.edu.uniquindio.application.dto.userDTO.UserDTO;
import co.edu.uniquindio.application.model.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {

    // Para crear entidad desde DTO (dejamos sin mapeos específicos por ahora)
    @BeanMapping(ignoreByDefault = true)
    User toEntity(CreateUserDTO createUserDTO);

    // Versión “neutral” de DTO (no setea campos hasta que arreglemos la entidad/DTO)
    @BeanMapping(ignoreByDefault = true)
    UserDTO toDTO(User user);

    // Alias para compatibilidad con el servicio (UserServiceImpl usa userMapper::toUserDTO)
    // Si más adelante necesitas un mapeo real, cambia la implementación a toDTO(user).
    default UserDTO toUserDTO(User user) {
        return toDTO(user);
    }
}
