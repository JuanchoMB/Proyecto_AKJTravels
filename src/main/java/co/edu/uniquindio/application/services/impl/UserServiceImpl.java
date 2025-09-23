package co.edu.uniquindio.application.services.impl;

import co.edu.uniquindio.application.dto.hostDTO.CreateHostDTO;
import co.edu.uniquindio.application.dto.userDTO.*;
import co.edu.uniquindio.application.exceptions.ValueConflictException;
import co.edu.uniquindio.application.mappers.UserMapper;
import co.edu.uniquindio.application.model.User;
import co.edu.uniquindio.application.repositories.UserRepository;
import co.edu.uniquindio.application.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public void create(CreateUserDTO userDTO) throws Exception {
        User newUser = userMapper.toEntity(userDTO);

        if(!existsByEmail(userDTO.email()));{
            throw new ValueConflictException("El Email ya existe en el sistema");
        }

        newUser.setPassword(passwordEncoder.encode(userDTO.password()));
        userRepository.save(newUser);
    }

    public boolean existsByEmail(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        return optionalUser.isPresent();
    }

    private boolean isEmailDuplicated(String email){
        return userStore.values().stream().anyMatch(
                u -> u.getEmail().equalsIgnoreCase(email)
        );
    }

    private String encode(String password){
        var passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    @Override
    public UserDTO get(String id) throws Exception {
        //Recuperación del usuario
        Optional<User> userOptional = userRepository.findById(id);

        //Validación del usuario
        if (userOptional.isEmpty()) {
            throw new Exception("Usuario no encontrado.");
        }

        //Transformación del usuario a DTO
        return userMapper.toUserDTO(userOptional.get());
    }

    @Override
    public void delete(String id) throws Exception {
        //Recuperación del usuario
        Optional<User> userOptional = userRepository.findById(id);

        //Validación del usuario
        if (userOptional.isEmpty()) {
            throw new Exception("Usuario no encontrado.");
        }

        //Eliminación del usuario
        userRepository.delete(userOptional.get());
    }

    @Override
    public void edit(String id, UpdateUserDto userDTO) throws Exception {

        //Recuperación del usuario
        Optional<User> userOptional = userRepository.findById(id);

        //Validación del usuario
        if (userOptional.isEmpty()) {
            throw new Exception("Usuario no encontrado.");
        }

        //Se obtiene el usuario que está dentro del Optional
        User user = userOptional.get();

        //Actualización de los datos del usuario
        user.setName(userDTO.name());
        user.setPhone(userDTO.phone());
        user.setBirthDate(userDTO.BirthDay());
        user.setPhotoUrl(userDTO.photoUrl());

        //Almacenamiento del usuario
        userRepository.save(user);
    }

    private User getUser ( String id) throws Exception{
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new Exception("Usuario no encontrado.");
        }
        return userOptional.get();
    }

    public void changePassword(ChangePasswordDTO changePasswordDTO) throws Exception {
        User user = getUser(changePasswordDTO.id());

        if(passwordEncoder.matches(changePasswordDTO.oldPassword().equals(user.getPassword()))){
            throw new ValueConflictException("La contraseña no coincide con su contraseña actual");

        }
        if(passwordEncoder.matches(changePasswordDTO.newPassword().equals(user.getPassword()))){
            throw new ValueConflictException("La contraseña no puede ser igual a la anterior");
        }
        user.setPassword(passwordEncoder.encode(changePasswordDTO.newPassword()));
        userRepository.save(user);
    }

    @Override
    public void resetPassword(ResetPasswordDTO resetPasswordDTO) throws Exception {

    }


    @Override
    public void createHost(CreateHostDTO createHostDTO) throws Exception {

    }


    @Override
    public List<UserDTO> listAll() {
        return userRepository.findAll()
                .stream().map(userMapper::toUserDTO)
                .toList();
    }
}