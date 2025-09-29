package co.edu.uniquindio.application.services.impl;

import co.edu.uniquindio.application.dto.hostDTO.CreateHostDTO;
import co.edu.uniquindio.application.dto.userDTO.*;
import co.edu.uniquindio.application.exceptions.*;
import co.edu.uniquindio.application.mappers.UserMapper;
import co.edu.uniquindio.application.model.User;
import co.edu.uniquindio.application.repositories.UserRepository;
import co.edu.uniquindio.application.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final Map<String, User> userStore = new ConcurrentHashMap<>();
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;


    private boolean isEmailDuplicated(String email){
        return userRepository.findByEmail(email).isPresent();
    }


    @Override
    public TokenDTO login(LoginDTO loginDTO) throws Exception {

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // Recuperar el usuario desde la base de datos
        User user = getUserByEmail(loginDTO.email());

        // Verificar si la contraseña es correcta usando el PasswordEncoder
        if(!passwordEncoder.matches(loginDTO.password(), user.getPassword())){
            throw new NotFoundException("El usuario no existe");
        }

        return new TokenDTO("OK");
    }

    @Override
    @Transactional
    public void create(CreateUserDTO createUserDTO) throws Exception {

        Optional<User> findUser = userRepository.findByEmail(createUserDTO.email());

        if (findUser.isPresent()) {
            throw new ValueConflictException("El email ya existe");
        }

        User user = userMapper.toEntity(createUserDTO);
        userRepository.save(user);

    }

    private User getUserById(String id) throws Exception{
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado."));
    }

    private User getUserByEmail(String email) throws Exception{
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado."));
    }



    @Override
    @Transactional
    public void edit(String id, EditUserDTO userDTO) throws Exception {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Usuario no encontrado"));
        // TODO: copiar campos desde userDTO -> user
        userRepository.save(user);

    }

    private boolean isEmailDuplicate(String email) {
        return userStore.values().stream().anyMatch(
                u -> u.getEmail().equalsIgnoreCase(email)
        );
    }

    private String encode(String password) {
        var passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    @Override
    @Transactional
    public void delete(String id) throws Exception {
        User user = userStore.get(id);
        if(user == null) {
            throw new ResourceNotFoundException("Usuario no encontrado");
        }
        userStore.remove(id);
    }

    @Override
    public UserDTO get(String id) throws Exception {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return userMapper.toUserDTO(user.get());
        }
        throw new ResourceNotFoundException("Usuario no encontrado");
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> listAll() {
        List<UserDTO> list = new ArrayList<>();

        for (User user : userStore.values()) {
            list.add(userMapper.toUserDTO(user));
        }
        return list;
    }




    @Override
    @Transactional
    public void resetPassword(ResetPasswordDTO resetPasswordDTO) throws Exception {
        // Esqueleto mínimo; implementa tu lógica real de reset (token/código, etc.)
        // Por ahora no hace nada para que compile.
    }

    @Override
    @Transactional
    public void createHost(CreateHostDTO createHostDTO) throws Exception {

    }

    private final UserRepository repo;


    @Override
    public UserDTO getById(String id) throws NotFoundException {
        User u = repo.findById(id).orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
        return userMapper.toUserDTO(u);
    }

    @Override
    public void changePassword(ChangePasswordDTO changePasswordDTO) throws Exception {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


        // Recuperar el usuario desde la base de datos
        User user = getUserById(changePasswordDTO.idUser());

        // Verificar que la contraseña actual coincida
        if(!passwordEncoder.matches(changePasswordDTO.oldPassword(), user.getPassword())){
            throw new ValidationException("La contraseña actual es incorrecta.");
        }

        // Verificar que la nueva contraseña sea diferente a la actual
        if(changePasswordDTO.oldPassword().equals(changePasswordDTO.newPassword())){
            throw new ValueConflictException("La nueva contraseña no puede ser igual a la actual.");
        }

        // Actualizar la contraseña
        user.setPassword( passwordEncoder.encode(changePasswordDTO.newPassword()) );

        // Guardar el usuario con la nueva contraseña
        userRepository.save(user);

    }

}
