package co.edu.uniquindio.application.services.impl;

import co.edu.uniquindio.application.dto.CreateUserDTO;
import co.edu.uniquindio.application.dto.EditUserDTO;
import co.edu.uniquindio.application.dto.UserDTO;
import co.edu.uniquindio.application.exceptions.ValueConflictException;
import co.edu.uniquindio.application.mappers.UserMapper;
import co.edu.uniquindio.application.model.User;
import co.edu.uniquindio.application.model.UserStatus;
import co.edu.uniquindio.application.services.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final Map<String, User> userStore = new ConcurrentHashMap<>();

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public void create(CreateUserDTO userDTO) throws Exception {
        // Validación para verificar si el email ya está en uso
        if(isEmailDuplicated(userDTO.email())){
            throw new ValueConflictException("El correo electrónico ya está en uso.");
        }

        // Transformación del DTO a User
        User newUser = userMapper.toEntity(userDTO);
        newUser.setPassword(encode(userDTO.password()));

        // Almacenamiento del usuario
        userStore.put(newUser.getId(), newUser);


        // Creación del nuevo usuario a partir del DTO
        newUser = User.builder()
                .id(UUID.randomUUID().toString())
                .name(userDTO.name())
                .email(userDTO.email())
                .phone(userDTO.phone())
                .role(userDTO.role())
                .dateBirth(userDTO.dateBirth())
                .photoUrl(userDTO.photoUrl())
                .password(userDTO.password())
                .createdAt(LocalDateTime.now())
                .status(UserStatus.ACTIVE)
                .build();

        // Almacenamiento del usuario
        userStore.put(newUser.getId(), newUser);
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
        // Recuperación del usuario
        User user = userStore.get(id);

        // Si el usuario no existe, lanzar una excepción
        if (user == null) {
            throw new Exception("Usuario no encontrado.");
        }

        // Transformación del usuario a DTO
        return userMapper.toUserDTO(user);

        // Mapeo del usuario a UserDTO
        /*return new UserDTO(
                user.getId(),
                user.getName(),
                user.getPhone(),
                user.getEmail(),
                user.getPhotoUrl(),
                user.getDateBirth(),
                user.getRole()
        );*/

    }

    @Override
    public void delete(String id) throws Exception {
        // Recuperación del usuario
        User user = userStore.get(id);

        // Si el usuario no existe, lanzar una excepción
        if (user == null) {
            throw new Exception("Usuario no encontrado.");
        }

        // Eliminación del usuario
        userStore.remove(id);
    }

    @Override
    public List<UserDTO> listAll() {
        return List.of();
    }

    @Override
    public void edit(String id, EditUserDTO userDTO) throws Exception {

    }
}