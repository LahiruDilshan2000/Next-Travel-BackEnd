package lk.ijse.identityserver.api;

import jakarta.validation.Valid;
import lk.ijse.identityserver.dto.AuthRequestDTO;
import lk.ijse.identityserver.dto.UserDTO;
import lk.ijse.identityserver.service.UserService;
import lk.ijse.identityserver.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author Lahiru Dilshan
 * @created Sat 12:03 PM on 10/21/2023
 * @project identity-server
 **/
@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping(value = "/register", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseUtil saveUser(@RequestPart("file") MultipartFile file,
                                 @Valid @RequestPart("user") UserDTO userDTO) throws IOException {

        userService.saveUser(file, userDTO);
        return ResponseUtil
                .builder()
                .code(200)
                .message("User save successful")
                .build();
    }


    @PostMapping(value = "/token", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseUtil getToken(@Valid @RequestBody AuthRequestDTO requestDTO) throws IOException {

        return ResponseUtil
                .builder()
                .code(200)
                .data(userService.generateToken(requestDTO))
                .message("User log in successful")
                .build();
    }

    @GetMapping(value = "/validate", params = {"token"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public String validateToken(@RequestParam String token) {

        userService.validateToken(token);
        return "Token is valid ";

    }
}
