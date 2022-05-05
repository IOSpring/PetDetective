package com.iospring.pets.petsfinder.user.controller;

import com.iospring.pets.petsfinder.user.dto.LoginRequestDto;
import com.iospring.pets.petsfinder.user.dto.LoginResponseDto;
import com.iospring.pets.petsfinder.user.dto.UserDTO;
import com.iospring.pets.petsfinder.user.dto.UserJoinDTO;
import com.iospring.pets.petsfinder.user.entity.User;
import com.iospring.pets.petsfinder.user.repositoru.LoginRepository;
import com.iospring.pets.petsfinder.user.repositoru.UserRepository;
import com.iospring.pets.petsfinder.user.service.UserService;
import com.iospring.pets.petsfinder.user.service.certificationService;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.Random;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final EntityManager em;
    private final certificationService cft;

    private final UserService userService;
    private final LoginRepository loginRepository;

// jongseo
    private final UserRepository userRepository;
    //




    @PostMapping("/check/sendSMS")
    public @ResponseBody
    LoginResponseDto sendSMS(@RequestBody LoginRequestDto form, HttpSession httpSession) {

        //   수신자 번호 :  phoneNumber
        //   인증번호 :  numStr
        // String numStr = cft.certifiedPhoneNumber(phoneNumber);
        System.out.println("phoneNumber = " + form.getPhoneNumber());
        LoginResponseDto loginResponseDto = userService.createLoginResponsedto(form.getPhoneNumber(), "1234");
        loginRepository.updateDviceToken(form.getPhoneNumber(), form.getDeviceToken());


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

    @GetMapping("/delete/{phoneNumber}")
    public void deleteUser(@PathVariable("phoneNumber") String phoneNumber){
        userService.delete(phoneNumber);
    }

    // Jong Seo


    @PostMapping("/login")
    public void login(String phoneNumber, String deviceToken, HttpSession httpSession) {
        System.out.println("phoneNumber = " + phoneNumber);
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new RuntimeException("User Not found"));
        /*
        if(유저가 입력한 인증번호 != 서버에서 만든 인증번호 ) {
            throw new RuntimeException("인증번호 일치하지 않습니다.");
        }
         */
        user.checkDeviceToken(deviceToken);

        httpSession.setAttribute("phoneNumber", user.getPhoneNumber());

        System.out.println("httpSession.getAttribute(\"phoneNumber\") = " + httpSession.getAttribute("phoneNumber"));

    }


}
