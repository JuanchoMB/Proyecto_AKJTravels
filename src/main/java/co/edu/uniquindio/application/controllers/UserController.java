package co.edu.uniquindio.application.controllers;

import co.edu.uniquindio.application.dto.ResponseDTO;
import co.edu.uniquindio.application.dto.hostDTO.CreateHostDTO;
import co.edu.uniquindio.application.dto.hostDTO.HostDTO;
import co.edu.uniquindio.application.dto.userDTO.*;
import co.edu.uniquindio.application.dto.accommodationDTO.AccommodationDTO;
import co.edu.uniquindio.application.services.AccommodationService;
import co.edu.uniquindio.application.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AccommodationService accommodationService;

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> create(@PathVariable String id,
                                                      @Valid @RequestBody UpdateUserDto updateUserDto) throws Exception {
        userService.edit(id, updateUserDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO<>(false, "usuario actualizado"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable String id,
                                                      @Valid @RequestBody DeleteUserDTO deleteUserDTO) throws Exception {
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO<>(false, "usuario eliminado"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<UserDTO>> get(@PathVariable String id) throws Exception {
        UserDTO dto = userService.get(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO<>(false, dto));
    }

    @PostMapping("/{id}/host")
    public ResponseEntity<ResponseDTO<String>> add_data_host(@PathVariable String id, @Valid @RequestBody CreateHostDTO createHostDTO) throws Exception {

        // ¡OJO! pasamos la VARIABLE, no el tipo
        userService.createHost(createHostDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO<>(false, "datos de host agregados"));
    }

    @PostMapping("/password/change")
    public ResponseEntity<ResponseDTO<String>> update_password(@Valid @RequestBody ChangePasswordDTO updateUserDto) throws Exception {
        userService.changePassword(updateUserDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO<>(false, "contraseña actualizada"));
    }

    @GetMapping("/{id}/accommodations/host")
    public ResponseEntity<ResponseDTO<List<AccommodationDTO>>> listAccommodationHost(@PathVariable String id) throws Exception {
        List<AccommodationDTO> list = accommodationService.listAllAccommodationsHost(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO<>(false, list));
    }
}
