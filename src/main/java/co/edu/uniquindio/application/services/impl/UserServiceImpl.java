package co.edu.uniquindio.application.services.impl;

import co.edu.uniquindio.application.dto.userDTO.CreateUserDTO;
import co.edu.uniquindio.application.dto.userDTO.UpdateUserDto;
import co.edu.uniquindio.application.dto.userDTO.UserDTO;
import co.edu.uniquindio.application.exceptions.ResourceNotFoundException;
import co.edu.uniquindio.application.mappers.UserMapper;
import co.edu.uniquindio.application.model.User;
import co.edu.uniquindio.application.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final Map<String, User> userStore = new ConcurrentHashMap<>();

    @Override
    public void create(CreateUserDTO userDTO) throws Exception {

        /*
        if(isEmailDuplicated(userDTO.email())){
            throw new ValueConflictException("El correo electrónico electronico ya está en uso.");
        }

        User newUser = userMapper.toEntity(userDTO);
        // coframos la contraseña
        newUser.setPassword(encode(newUser.getPassword()));

        userStore.put(newUser.getId(), newUser);*/
    }

    @Override
    public void edit(String id, UpdateUserDto userDTO) throws Exception {

    }

    private boolean isEmailDuplicated(String email){
        return userStore.values().stream().anyMatch(
                u -> u.getEmail().equalsIgnoreCase(email)
        );
    }


    // para encriptar la contraseña
    private String encode(String password){
        var passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }


    @Override
    public void delete(String id) throws Exception {
        User user = userStore.get(id);

        if(user == null){
            throw new ResourceNotFoundException("usuario no encontrado");
        }
        userStore.remove(id);
    }

    //devuelve datos del usuario según el id
    @Override
    public UserDTO get(String id) throws Exception {

        User user = userStore.get(id);
        if(user == null){
            throw new ResourceNotFoundException("El usuario no existe");
        }

        return userMapper.toUserDTO(user);
    }

    @Override
    public List<UserDTO> listAll() {
        List<UserDTO> list = new ArrayList<>();

        for (User user : userStore.values()) {
            list.add(userMapper.toUserDTO(user));
        }

        return list;
    }
}

    /*private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public void create(CreateUserDTO userDTO) throws Exception {
        User newUser = userMapper.toEntity(userDTO);

        if(existsByEmail(userDTO.email()) == false);{
            throw new ValueConflictException("El Email ya existe en el sistema");
        }

        newUser.setPassword(passwordEncoder.encode(userDTO.password()));
        userRepository.save(newUser);
    }

    public boolean existsByEmail(String email) {
        Optional <User> optionalUser = userRepository.findByEmail(email);
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
        User user = userRepository.findById(id)

    }

    @Override
    public void delete(String id) throws Exception {
        Optional<User> optionalUser = userRepository.findById(id);

        if(optionalUser.isEmpty()){

        }

    }


    @Override
    public void edit(String id, EditUserDTO userDTO) throws Exception {

        User user = getUser(id);
        userMapper.updateUserFromDto(userDTO, user);
        userRepository.save(user);
    }

    private User getUser ( Stiring id) throws Exception{
        Optional<>

    }

    public void changePassword(ChangePasswordDTO changePasswordDTO) throws Exception {
        User user = getUser(changePasswordDTO.id());

        if(passwordEncoder.matches(changePasswordDTO.oldPassword().user.getPassword())){
            throw new ValueConflictException("La contraseña no coincide con su contraseña actual");

        }
        if(passwordEncoder.matches(changePasswordDTO.newPassword().user.getPassword())){
            throw new ValueConflictException("La contraseña no puede ser igual a la anterior");
        }
        user.setPassword(passwordEncoder.encode(changePasswordDTO.newPassword()));
        userRepository.save(user);
    }

    @Override
    public void resetPassword(ResetPasswordDTO resetPasswordDTO) throws Exception {
        Optional<Pass>
    }

    @Override
    public void createHost(CreateHostDTO createHostDTO) throws Exception {

    }


    @Override
    public List<UserDTO> listAll() {
        return List.of();
    }
}*/