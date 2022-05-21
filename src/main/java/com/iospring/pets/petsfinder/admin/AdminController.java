package com.iospring.pets.petsfinder.admin;


import com.iospring.pets.petsfinder.user.dto.UserDTO;
import com.iospring.pets.petsfinder.user.entity.User;
import com.iospring.pets.petsfinder.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminController {


    private final UserRepository userRepository;

    @GetMapping("/admin/user")
    public List<User> getAllUser() {
        return userRepository.findAll();
    }
}
