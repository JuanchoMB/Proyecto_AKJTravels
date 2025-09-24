package co.edu.uniquindio.application.dto.userDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class ChangePasswordDTO {

    @NotBlank
    private String userId;
    private String currentPassword; // opcional
    private String oldPassword;     // opcional

    @NotBlank
    @Size(min = 7, max = 50)
    private String newPassword;
    private String confirmPassword;

    public ChangePasswordDTO() {}

    public ChangePasswordDTO(String userId, String currentPassword, String oldPassword,
                             String newPassword, String confirmPassword) {
        this.userId = userId;
        this.currentPassword = currentPassword;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
    }

    /* Getters
    public String getUserId() { return userId; }
    public String getCurrentPassword() { return currentPassword; }
    public String getOldPassword() { return oldPassword; }
    public String getNewPassword() { return newPassword; }
    public String getConfirmPassword() { return confirmPassword; }*/

    /* Setters
    public void setUserId(String userId) { this.userId = userId; }
    public void setCurrentPassword(String currentPassword) { this.currentPassword = currentPassword; }
    public void setOldPassword(String oldPassword) { this.oldPassword = oldPassword; }
    public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }*/
}
