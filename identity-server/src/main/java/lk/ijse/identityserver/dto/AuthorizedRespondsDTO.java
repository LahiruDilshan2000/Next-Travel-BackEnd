package lk.ijse.identityserver.dto;

import lk.ijse.identityserver.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Lahiru Dilshan
 * @created Sun 8:34 AM on 10/22/2023
 * @project identity-server
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AuthorizedRespondsDTO {

    private String token;

    private Role role;

    private String username;

    private String nic;

    private String email;

    private byte[] imgArray;
}
