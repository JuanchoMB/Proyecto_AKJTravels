package co.edu.uniquindio.application.services;

import co.edu.uniquindio.application.dto.authDTO.LoginDTO;
import co.edu.uniquindio.application.dto.authDTO.TokenDTO;
import co.edu.uniquindio.application.dto.hostDTO.HostDTO;
import co.edu.uniquindio.application.dto.userDTO.*;
import co.edu.uniquindio.application.model.User;

public interface UserService {

    void create(CreateUserDTO userDTO) throws Exception;
    UserDTO get(String id) throws Exception;
    void edit(String id, EditUserDTO editUserDTO) throws Exception;
    void addDataHost(String id, HostDTO hostDTO) throws Exception;
    void delete(String id, DeleteUserDTO deleteUserDTO) throws Exception;
    User findByEmail(String email);
    TokenDTO login(LoginDTO loginDTO) throws Exception;
    void changePassword(String id, EditPasswordDTO editPasswordDTO) throws Exception;



}