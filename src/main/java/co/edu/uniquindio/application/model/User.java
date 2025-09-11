package co.edu.uniquindio.application.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class User {
    private String id;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private Role role;

    public User(String id, String username, String password, String email, String phoneNumber, Role role) {

        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;


    }

}
