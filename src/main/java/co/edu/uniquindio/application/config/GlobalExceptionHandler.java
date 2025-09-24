package co.edu.uniquindio.application.config;

import co.edu.uniquindio.application.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFound(NotFoundException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", true);
        body.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", true);
        Map<String, String> fields = new HashMap<>();
        for (var e : ex.getBindingResult().getFieldErrors()) {
            fields.put(e.getField(), e.getDefaultMessage());
        }
        body.put("fields", fields);
        return ResponseEntity.badRequest().body(body);
    }
}
