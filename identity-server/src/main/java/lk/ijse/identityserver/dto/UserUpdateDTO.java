package lk.ijse.identityserver.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Lahiru Dilshan
 * @created Sun 6:44 PM on 10/29/2023
 * @project identity-server
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserUpdateDTO {

    @NotNull(message = "User email cannot be null !")
    @NotBlank(message = "User email cannot be empty !")
    @NotEmpty(message = "User email cannot be empty !")
    @Pattern(regexp = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,}$" , message = "User email invalid or empty !")
    @Email
    private String email;

    @NotNull(message = "User name cannot be null !")
    @NotBlank(message = "User name cannot be empty !")
    @NotEmpty(message = "User name cannot be empty !")
    @Pattern(regexp = "^([A-Za-z ]{3,})$" , message = "User name invalid or empty !")
    private String userName;

    @NotNull(message = "Old password cannot be null !")
    @NotBlank(message = "Old password cannot be empty !")
    @NotEmpty(message = "Old password cannot be empty !")
    @Pattern(regexp = "^([A-Za-z0-9@]{4,})$" , message = "Old password invalid or empty !")
    private String oldPassword;

    @NotNull(message = "New password cannot be null !")
    @NotBlank(message = "New password cannot be empty !")
    @NotEmpty(message = "New password cannot be empty !")
    @Pattern(regexp = "^([A-Za-z0-9@]{4,})$" , message = "New password invalid or empty !")
    private String newPassword;
}
