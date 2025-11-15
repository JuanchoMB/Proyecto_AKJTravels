package co.edu.uniquindio.application.model;

import co.edu.uniquindio.application.model.enums.State;
import jakarta.persistence.*;
import lombok.*;
import co.edu.uniquindio.application.model.enums.Role;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users") // opcional pero recomendado, `user` es palabra reservada en algunos motores
public class User {

    @Id
    private String id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(length = 50)
    private String lastName;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Column(length = 15)
    private String phone;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false)
    private String country;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private String password;

    /**
     * FOTO DE PERFIL
     * - Hacemos photoUrl NULLABLE para permitir usuarios sin foto.
     * - Guardamos photoPublicId para poder borrar en Cloudinary la imagen anterior.
     */
    @Column(length = 300)          // quitar nullable=false
    private String photoUrl;       // puede ser null

    @Column(length = 200)
    private String photoPublicId;  // nuevo campo, puede ser null

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private State state;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private Boolean isHost;
}
