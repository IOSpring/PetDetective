package com.iospring.pets.petsfinder.user;

import com.iospring.pets.petsfinder.user.dto.UserDTO;
import com.iospring.pets.petsfinder.user.dto.UserJoinDTO;
import com.iospring.pets.petsfinder.user.entity.User;
import com.iospring.pets.petsfinder.user.repositoru.UserRepository;
import com.iospring.pets.petsfinder.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class AuthController {

    private final UserService userService;
    private final UserRepository userRepository;
    @PostMapping("/join")
    public UserDTO joinUser(@RequestBody UserJoinDTO userJoinDTO) {
        User newUser = userService.join(userJoinDTO);

        return UserDTO.createUserDTO(newUser);
    }

    @GetMapping("/users")
    public List<UserDTO> findUser() {
        List<Object[]> usersIn10KM = userRepository.findUsersIn10KM(48.64425432574, 148.994312246826);
        List<UserDTO> userDTOFromObject = userRepository.createUserDTOFromObject(usersIn10KM);
        return userDTOFromObject;
    }
}
