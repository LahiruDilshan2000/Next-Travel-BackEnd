package lk.ijse.identityserver.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Lahiru Dilshan
 * @created Sat 12:24 PM on 10/21/2023
 * @project identity-server
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuthRequestDTO {

    @NotNull(message = "User email cannot be null !")
    @NotBlank(message = "User email cannot be empty !")
    @NotEmpty(message = "User email cannot be empty !")
    @Pattern(regexp = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,}$" , message = "User email invalid or empty !")
    @Email
    private String email;

    @NotNull(message = "User password cannot be null !")
    @NotBlank(message = "User password cannot be empty !")
    @NotEmpty(message = "User password cannot be empty !")
    @Pattern(regexp = "^([A-Za-z0-9@]{4,})$" , message = "User password invalid or empty !")
    private String password;
}
