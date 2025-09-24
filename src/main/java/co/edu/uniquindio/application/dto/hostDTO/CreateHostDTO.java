package co.edu.uniquindio.application.dto.hostDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateHostDTO {

    // Setters
    // Getters
    @NotBlank
    private String userId;

    // Campos opcionales de ejemplo (añade los reales cuando los tengas)
    private String about;
    private String phone;

    public CreateHostDTO() {}

    public CreateHostDTO(String userId, String about, String phone) {
        this.userId = userId;
        this.about = about;
        this.phone = phone;
    }

}
