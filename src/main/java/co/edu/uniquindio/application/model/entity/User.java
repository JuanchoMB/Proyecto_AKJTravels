package co.edu.uniquindio.application.model.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    public String name;
    public String email;
    public String password;
    public String role;
    public String id;
}
