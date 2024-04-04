package springProj.nutrDB.models.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserDTO {

    @Email(message = "Vyplňte validní email.")
    @NotBlank(message = "Vyplňte uživatelský email.")
    private String email;

    @NotBlank(message = "Vyplňte heslo.")
    @Size(min = 5, message = "Heslo musí mít alespoň 5 znaků.")
    private String password;

    @NotBlank(message = "Vyplňte heslo.")
    @Size(min = 5, message = "Heslo musí mít alespoň 5 znaků.")
    private String passwordConfirmation;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }
}
