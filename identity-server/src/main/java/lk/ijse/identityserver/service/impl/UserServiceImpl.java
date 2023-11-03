package lk.ijse.identityserver.service.impl;

import lk.ijse.identityserver.dto.AuthRequestDTO;
import lk.ijse.identityserver.dto.AuthorizedRespondsDTO;
import lk.ijse.identityserver.dto.UserDTO;
import lk.ijse.identityserver.dto.UserUpdateDTO;
import lk.ijse.identityserver.entity.Role;
import lk.ijse.identityserver.entity.User;
import lk.ijse.identityserver.exception.DuplicateException;
import lk.ijse.identityserver.exception.NotFoundException;
import lk.ijse.identityserver.repository.UserRepository;
import lk.ijse.identityserver.service.JwtService;
import lk.ijse.identityserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Lahiru Dilshan
 * @created Sat 10:52 AM on 10/21/2023
 * @project identity-server
 **/
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final ModelMapper modelMapper;

    private final AuthenticationManager authenticationManager;
    private final String mainPath = "C:\\Images\\User\\";

    @Override
    public void saveUser(MultipartFile file, UserDTO userDTO) throws IOException {

        if (userRepository.existsByEmail(userDTO.getEmail()))
            throw new DuplicateException(userDTO.getEmail() + " user email already exist !");

        if (userRepository.existsByNic(userDTO.getNic()))
            throw new DuplicateException(userDTO.getNic() + " User nic already exist !");

        String folderPath = mainPath + UUID.randomUUID();

        File pathFile = new File(folderPath);
        if (!pathFile.mkdir())
            throw new RuntimeException("User Image directory create failed !");

        String imagePth = folderPath + "\\" + file.getOriginalFilename();
        file.transferTo(Paths.get(imagePth));

        User user = modelMapper.map(userDTO, User.class);
        user.setFolderPath(folderPath);
        user.setUserImage(imagePth);
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(user);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, MultipartFile file) throws IOException {

        if (!userRepository.existsById(userDTO.getUserId()))
            throw new NotFoundException(userDTO.getUserId() + " User doesn't exist !");

        User existUser = userRepository.findById(userDTO.getUserId()).get();

        if (!existUser.getEmail().equalsIgnoreCase(userDTO.getEmail())){
            if (userRepository.existsByEmail(userDTO.getEmail()))
                throw new DuplicateException(userDTO.getEmail() + " user email already exist !");
        }

        if (!existUser.getNic().equalsIgnoreCase(userDTO.getNic())){
            if (userRepository.existsByNic(userDTO.getNic()))
                throw new DuplicateException(userDTO.getNic() + " User nic already exist !");
        }

        deleteExistingImg(existUser.getUserImage());

        User user = modelMapper.map(userDTO, User.class);
        user.setFolderPath(existUser.getFolderPath());
        user.setUserImage(saveAndGetPath(existUser.getFolderPath(), file));
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        return modelMapper.map(userRepository.save(user), UserDTO.class);
    }

    private String saveAndGetPath(String folderPath, MultipartFile file) throws IOException {

        String imgPath = folderPath + "\\" + file.getOriginalFilename();
        file.transferTo(Paths.get(imgPath));
        return imgPath;
    }

    private void deleteExistingImg(String imagePath) {

        File oldFile = new File(imagePath);

        if (!oldFile.exists()) {
            throw new NotFoundException("Existing user image is not found !");
        }

        if (!oldFile.delete())
            throw new RuntimeException("Existing user image delete failed !");
    }

    @Override
    public UserDTO updateUserUserNameAndPassword(UserUpdateDTO userUpdateDTO) {

        Optional<User> user = userRepository.findByEmail(userUpdateDTO.getEmail());

        if (user.isEmpty())
            throw new NotFoundException(userUpdateDTO.getEmail() + " User doesn't exist !");

        User prsentUser = user.get();

        if (!passwordEncoder.matches(userUpdateDTO.getOldPassword(), prsentUser.getPassword()))
            throw new RuntimeException("Old password is incorrect !");

        prsentUser.setUserName(userUpdateDTO.getUserName());
        prsentUser.setPassword(passwordEncoder.encode(userUpdateDTO.getNewPassword()));

        return modelMapper.map(userRepository.save(prsentUser), UserDTO.class);
    }

    @Override
    public void deleteUser(Integer userId) {

        if (!userRepository.existsById(userId))
            throw new NotFoundException(userId + " User doesn't exist !");

        User user = userRepository.findById(userId).get();
        deleteExistingImg(user.getUserImage());

        File folder = new File(user.getFolderPath());

        if (!folder.delete())
            throw new RuntimeException("User Image directory delete failed !");

        userRepository.deleteById(userId);
    }

    @Override
    public List<UserDTO> getPageableUsers(Integer page, Integer count) {

        PageRequest pageRequest = PageRequest.of(page, count);

        return userRepository
                .getUserHQLWithPageable(Role.USER, pageRequest)
                .stream()
                .map(this::getDTO)
                .toList();
    }

    @Override
    public List<UserDTO> getAll() {

        return userRepository
                .findAll(Role.USER)
                .stream()
                .map(this::getDTO)
                .toList();
    }

    @Override
    public UserDTO findByNic(String nic) {

        Optional<User> isExist = userRepository.findByNic(nic);

        if (isExist.isEmpty())
            throw new NotFoundException(nic + " User doesn't exist !");

        return getDTO(isExist.get());
    }

    private UserDTO getDTO(User user) {

        UserDTO userDTO = modelMapper.map(user, UserDTO.class);

        try {

            byte[] userImage = Files.readAllBytes(new File(user.getUserImage()).toPath());

            if (userImage.length == 0)
                throw new NotFoundException(userDTO.getNic() + " User image not found !");

            userDTO.setUserImage(userImage);
            return userDTO;

        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public AuthorizedRespondsDTO generateToken(AuthRequestDTO authRequestDTO) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequestDTO.getEmail(),
                        authRequestDTO.getPassword()
                )
        );
        User user = userRepository.findByEmail(authRequestDTO.getEmail())
                .orElseThrow(() -> new NotFoundException("User not found"));

        return AuthorizedRespondsDTO
                .builder()
                .token(jwtService.generateToken(authRequestDTO.getEmail(), user.getRole()))
                .username(user.getUserName())
                .nic(user.getNic())
                .email(user.getEmail())
                .imgArray(getImgArray(user.getUserImage()))
                .role(user.getRole())
                .build();
    }

    private byte[] getImgArray(String path) {

        try {
            if (path != null) {

                return Files.readAllBytes(new File(path).toPath());
            }
            return null;

        } catch (IOException e) {
            throw new NotFoundException("Image not found");
        }
    }

    @Override
    public void validateToken(String token) {

        jwtService.validateToken(token);
    }

    @Override
    public List<UserDTO> searchByText(String text) {

        text = "%" + text + "%";

        return userRepository
                .searchByText(text, text, text, text)
                .stream()
                .filter(user -> user.getRole().equals(Role.USER))
                .map(this::getDTO)
                .toList();

    }
}
