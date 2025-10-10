package co.edu.uniquindio.application.services;

import co.edu.uniquindio.application.dto.authDTO.LoginDTO;
import co.edu.uniquindio.application.dto.authDTO.TokenDTO;

import co.edu.uniquindio.application.dto.hostDTO.HostDTO;
import co.edu.uniquindio.application.dto.userDTO.*;
import co.edu.uniquindio.application.model.User;

public interface UserService {


    //Registro de Usuarios - POST /users
    void create(CreateUserDTO userDTO) throws Exception;

    //Obtener usuario por id - GET /user/{id}
    UserDTO get(String id) throws Exception;

    //Actualizar/Editar datos del usuario - PUT /users/{id}
    void edit(String id, EditUserDTO editUserDTO) throws Exception;

    //que pasa si el usuario al principio no quiso poner un dato y leugo si, preguntar profe
    void addHostData(String id, HostDTO hostDTO) throws Exception;

    //Eliminar Usuario - DELETE /user/{id}
    void delete(String id, DeleteUserDTO deleteUserDTO) throws Exception;

    User findByEmail(String email);

    //login
    TokenDTO login(LoginDTO loginDTO) throws Exception;

    void changePassword(String id, EditPasswordDTO editPasswordDTO) throws Exception;



}