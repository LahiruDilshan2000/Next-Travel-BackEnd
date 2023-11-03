package lk.ijse.identityserver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lk.ijse.identityserver.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Lahiru Dilshan
 * @created Sat 12:05 PM on 10/21/2023
 * @project identity-server
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {

    private Integer userId;

    @NotNull(message = "User name cannot be null !")
    @NotBlank(message = "User name cannot be empty !")
    @NotEmpty(message = "User name cannot be empty !")
    @Pattern(regexp = "^([A-Za-z ]{3,})$" , message = "User name invalid or empty !")
    private String userName;

    @NotNull(message = "User nic cannot be null !")
    @NotBlank(message = "User nic cannot be empty !")
    @NotEmpty(message = "User nic cannot be empty !")
    @Pattern(regexp = "^([A-Za-z0-9]{10,})$" , message = "User nic invalid or empty !")
    private String nic;

    @NotNull(message = "User address cannot be null !")
    @NotBlank(message = "User address cannot be empty !")
    @NotEmpty(message = "User address cannot be empty !")
    @Pattern(regexp = "^[A-Za-z ]+$" , message = "User address invalid or empty !")
    private String address;

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
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Null(message = "Ops ss role will added by backend !")
    private Role role;

    @Null(message = "User image added in after !")
    private byte[] userImage;

}
