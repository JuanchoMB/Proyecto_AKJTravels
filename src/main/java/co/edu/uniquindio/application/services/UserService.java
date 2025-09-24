package co.edu.uniquindio.application.services;

import co.edu.uniquindio.application.dto.hostDTO.CreateHostDTO;
import co.edu.uniquindio.application.dto.userDTO.*;

import java.util.List;

public interface UserService {


    void create(CreateUserDTO dto) throws Exception;
    void edit(String id, UpdateUserDto userDTO) throws Exception;
    void delete(String id) throws Exception;
    UserDTO get(String id) throws Exception;
    UserDTO getById(String id);
    void changePassword(ChangePasswordDTO changePasswordDTO) throws Exception;
    void resetPassword(ResetPasswordDTO resetPasswordDTO) throws Exception;
    void createHost(CreateHostDTO createHostDTO) throws Exception;
    List<UserDTO> listAll();

}
