package co.edu.uniquindio.application.services;

import co.edu.uniquindio.application.dto.userDTO.CreateUserDTO;
import co.edu.uniquindio.application.dto.userDTO.UpdateUserDto;
import co.edu.uniquindio.application.dto.userDTO.UserDTO;
import java.util.List;

public interface UserService {


    void create(CreateUserDTO userDTO) throws Exception;
    void edit(String id, UpdateUserDto userDTO) throws Exception;
    void delete(String id) throws Exception;
    UserDTO get(String id) throws Exception;
    List<UserDTO> listAll();

}
