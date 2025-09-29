package co.edu.uniquindio.application.services;

import co.edu.uniquindio.application.dto.hostDTO.CreateHostDTO;
import co.edu.uniquindio.application.dto.userDTO.*;
import co.edu.uniquindio.application.exceptions.NotFoundException;

import java.util.List;

public interface UserService {

    TokenDTO login(LoginDTO loginDTO) throws Exception;
    void create(CreateUserDTO dto) throws Exception;
    void edit(String id, EditUserDTO userDTO) throws Exception;
    void delete(String id) throws Exception;
    UserDTO get(String id) throws Exception;
    UserDTO getById(String id) throws NotFoundException;
    void changePassword(ChangePasswordDTO changePasswordDTO) throws Exception;
    void resetPassword(ResetPasswordDTO resetPasswordDTO) throws Exception;
    void createHost(CreateHostDTO createHostDTO) throws Exception;
    List<UserDTO> listAll();

}
