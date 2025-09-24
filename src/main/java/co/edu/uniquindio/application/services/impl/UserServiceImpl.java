package co.edu.uniquindio.application.services.impl;

import co.edu.uniquindio.application.dto.hostDTO.CreateHostDTO;
import co.edu.uniquindio.application.dto.userDTO.*;
import co.edu.uniquindio.application.exceptions.BadRequestException;
import co.edu.uniquindio.application.exceptions.NotFoundException;
import co.edu.uniquindio.application.exceptions.UnauthorizedException;
import co.edu.uniquindio.application.mappers.UserMapper;
import co.edu.uniquindio.application.model.User;
import co.edu.uniquindio.application.repositories.UserRepository;
import co.edu.uniquindio.application.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    // ========= CRUD BÁSICO =========

    @Override
    @Transactional
    public void create(CreateUserDTO userDTO) throws Exception {
        // Esqueleto mínimo: crear entidad y guardar.
        User user = userMapper.toEntity(userDTO);
        if (user == null) user = new User(); // por si el mapper es neutro
        if (user.getId() == null || user.getId().isBlank()) {
            user.setId(UUID.randomUUID().toString());
        }
        if (user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void edit(String id, UpdateUserDto userDTO) throws Exception {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Usuario no encontrado"));
        // TODO: copiar campos desde userDTO -> user (cuando tengas los atributos definidos)
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void delete(String id) throws Exception {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Usuario no encontrado"));
        userRepository.delete(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO get(String id) throws Exception {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Usuario no encontrado"));
        // alias seguro (lo definimos en el mapper): toUserDTO -> toDTO
        return userMapper.toUserDTO(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> listAll() {
        // Esqueleto: devuelve lista vacía hasta mapear bien
        return Collections.emptyList();
    }

    // ========= CONTRASEÑAS / HOST =========

    @Override
    @Transactional
    public void changePassword(ChangePasswordDTO dto) throws Exception {
        // Usa getters (porque tu DTO no es record)
        String userId = dto.getUserId();

        var user = userRepository.findById(userId)
                .orElseThrow(() -> new UnauthorizedException("Usuario no encontrado"));

        String current = dto.getCurrentPassword() != null
                ? dto.getCurrentPassword()
                : dto.getOldPassword();

        if (current == null || !passwordEncoder.matches(current, user.getPassword())) {
            throw new UnauthorizedException("La contraseña actual es incorrecta");
        }

        if (passwordEncoder.matches(dto.getNewPassword(), user.getPassword())) {
            throw new BadRequestException("La nueva contraseña no puede ser igual a la actual");
        }

        if (dto.getConfirmPassword() != null && !dto.getNewPassword().equals(dto.getConfirmPassword())) {
            throw new BadRequestException("La confirmación de contraseña no coincide");
        }

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);
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
        // Esqueleto mínimo; implementa la lógica real cuando definas el modelo de Host
    }

    private final UserRepository repo;


    @Override
    public UserDTO getById(String id) {
        User u = repo.findById(id).orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
        return toDTO(u);
    }


    private UserDTO toDTO(User u) {
        String birth = u.getBirthDate() == null ? null : u.getBirthDate().toString();
        return new UserDTO(u.getId(), u.getName(), u.getEmail(), u.getPhone(), birth);
    }
}
