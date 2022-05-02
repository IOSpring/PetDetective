package com.iospring.pets.petsfinder.user.controller;

import com.iospring.pets.petsfinder.user.dto.LoginResponseDto;
import com.iospring.pets.petsfinder.user.dto.UserDTO;
import com.iospring.pets.petsfinder.user.dto.UserJoinDTO;
import com.iospring.pets.petsfinder.user.entity.User;
import com.iospring.pets.petsfinder.user.repositoru.LoginRepository;
import com.iospring.pets.petsfinder.user.repositoru.UserRepository;
import com.iospring.pets.petsfinder.user.service.UserService;
import com.iospring.pets.petsfinder.user.service.certificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.util.Random;

@RestController
@RequiredArgsConstructor
public class LoginController {
    private final EntityManager em;
    private final certificationService cft;

    private final UserService userService;
    private final LoginRepository loginRepository;
    @PostMapping("/check/sendSMS")
    public @ResponseBody
    LoginResponseDto sendSMS(String phoneNumber, String diviceToken) {

//        수신자 번호 :  phoneNumber
//        인증번호 :  numStr
        String numStr = cft.certifiedPhoneNumber(phoneNumber);
        LoginResponseDto loginResponseDto=userService.createLoginResponsedto(phoneNumber, numStr);
        loginRepository.updateDviceToken(phoneNumber,diviceToken);

        // 유저 휴대폰 <- 인증 번호 발송
        // 프론트 <- 인증번호, 회원가입 필요여부 반환
        return loginResponseDto;
    }


    @PostMapping("/join")
    public Long joinUser(@RequestBody UserJoinDTO userJoinDTO) {
        User newUser = userService.join(userJoinDTO);

        return newUser.getId();
    }


}
