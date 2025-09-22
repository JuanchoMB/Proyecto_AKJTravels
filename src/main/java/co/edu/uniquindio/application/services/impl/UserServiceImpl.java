package co.edu.uniquindio.application.services.impl;

import co.edu.uniquindio.application.dto.*;
import co.edu.uniquindio.application.exceptions.ValueConflictException;
import co.edu.uniquindio.application.mappers.UserMapper;
import co.edu.uniquindio.application.model.entity.User;
import co.edu.uniquindio.application.repositories.UserRepository;
import co.edu.uniquindio.application.services.UserService;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final
    private final Map<String, User> userStore = new ConcurrentHashMap<>();

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
}