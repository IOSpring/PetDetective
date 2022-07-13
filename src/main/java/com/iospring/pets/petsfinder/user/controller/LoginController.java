package com.iospring.pets.petsfinder.user.controller;

import com.iospring.pets.petsfinder.exception.CustomException;
import com.iospring.pets.petsfinder.exception.ErrorCode;
import com.iospring.pets.petsfinder.user.dto.*;
import com.iospring.pets.petsfinder.user.entity.User;
import com.iospring.pets.petsfinder.user.repository.LoginRepository;
import com.iospring.pets.petsfinder.user.repository.UserRepository;
import com.iospring.pets.petsfinder.user.service.UserService;
import com.iospring.pets.petsfinder.user.service.certificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;
    private final LoginRepository loginRepository;
    private final certificationService certificationService;
    private final UserRepository userRepository;


    @PostMapping("/check/sendSMS")
    public @ResponseBody
    LoginResponseDto sendSMS(@RequestBody LoginRequestDto form, HttpSession httpSession) {

        //   수신자 번호 :  phoneNumber
        //   인증번호 :  numStr
//         String numStr = certificationService.certifiedPhoneNumber(form.getPhoneNumber());
        LoginResponseDto loginResponseDto = userService.createLoginResponsedto(form.getPhoneNumber(), "1234");
        loginRepository.updateDeviceToken(form.getPhoneNumber(), form.getDeviceToken());


        httpSession.setAttribute("phoneNumber", form.getPhoneNumber());
        // 유저 휴대폰 <- 인증 번호 발송
        // 프론트 <- 인증번호, 회원가입 필요여부 반환
        return loginResponseDto;
    }


    @PostMapping("/join")
    public Long joinUser(@RequestBody UserJoinDTO userJoinDTO) {
        User newUser = userService.join(userJoinDTO);
        return newUser.getId();
    }
    @DeleteMapping("/delete/{phoneNumber}")
    public void deleteUser(@PathVariable("phoneNumber") String phoneNumber) {
        System.out.println("휴대폰 번호 : " + phoneNumber);
        userService.delete(phoneNumber);
    }


    @PutMapping("/user/update/location")
    public void updateLocation(@RequestBody UserLocationDto userLocationDto) {
        System.out.println("userLocationDto = " + userLocationDto);
        userService.updateLocation2(userLocationDto);
    }
}
