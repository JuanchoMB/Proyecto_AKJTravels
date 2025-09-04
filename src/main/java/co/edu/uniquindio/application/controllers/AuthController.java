package co.edu.uniquindio.application.controllers;

import co.edu.uniquindio.application.dto.LoginDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    //private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginDTO loginDTO) throws Exception {
        //authService.login(loginDTO);// FALLA ->
        return ResponseEntity.status(200).body("Login coorrecto");
    }
}