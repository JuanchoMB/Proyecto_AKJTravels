package co.edu.uniquindio.application.mappers;
import co.edu.uniquindio.application.dto.userDTO.CreateUserDTO;
import co.edu.uniquindio.application.dto.userDTO.EditUserDTO;
import co.edu.uniquindio.application.dto.userDTO.UserDTO;
import co.edu.uniquindio.application.model.User;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {

    //convierte de dto a entidad y viceversa, crea automaticamente el id, estado y fecha de creación de la cuenta
    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID().toString())")
    @Mapping(target = "state", constant = "ACTIVE")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "isHost", constant = "false")
    @Mapping(target = "description", ignore = true)
    User toEntity(CreateUserDTO createUserDTO);

    UserDTO toUserDTO(User user);


    //metodo para actualizar usuario existente
    //¿Por que no se puede usar beenMapping?
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "country", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "isHost", ignore = true)
    @Mapping(target = "description", ignore = true)
    void editUserFromDto(EditUserDTO editUserDTO, @MappingTarget User user);





}