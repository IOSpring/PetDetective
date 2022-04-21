package com.iospring.pets.petsfinder.chat.controller;


import com.iospring.pets.petsfinder.user.dto.UserDTO;
import com.iospring.pets.petsfinder.user.entity.User;
import com.iospring.pets.petsfinder.user.repositoru.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@AllArgsConstructor
@RestController
public class ChatController {
    final public UserRepository userRepository;

    @GetMapping("/chat")
    public void test() {

        User userA = userRepository.findByPhoneNumber("01099043322").orElseThrow(() -> new RuntimeException("No User"));
        UserDTO userADTO = UserDTO.createUserDTO(userA);
        User userB = userRepository.findByPhoneNumber("01099043323").orElseThrow(() -> new RuntimeException("No User"));
        UserDTO userBDTO = UserDTO.createUserDTO(userB);




        MultiValueMap<String, UserDTO> params = new LinkedMultiValueMap<>();
        params.add("userA", userADTO);
        params.add("userB", userBDTO);
        params.add("loggedInUser", userADTO);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<MultiValueMap<String, UserDTO>> entity = new HttpEntity<>(params, headers);

        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "http://localhost:3100",
                HttpMethod.POST,
                entity,
                String.class
        );

//        return "redirect:http://localhost:3100";
    }
}
