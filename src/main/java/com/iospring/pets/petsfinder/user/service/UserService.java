package com.iospring.pets.petsfinder.user.service;

import com.iospring.pets.petsfinder.detectiveBoard.dto.DetectiveBoardDTO;
import com.iospring.pets.petsfinder.user.dto.LoginResponseDto;
import com.iospring.pets.petsfinder.user.dto.UserDTO;
import com.iospring.pets.petsfinder.user.dto.UserJoinDTO;
import com.iospring.pets.petsfinder.user.entity.User;
import com.iospring.pets.petsfinder.user.repositoru.LoginRepository;
import com.iospring.pets.petsfinder.user.repositoru.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final LoginRepository loginRepository;

    private boolean isDupPhone(String phoneNumber) {
        User user = loginRepository.findOneUserByPhoneNum(phoneNumber);

        return user == null ? false : true;
    }
    public LoginResponseDto createLoginResponsedto(String phoneNumber,String cernum){
        User user = loginRepository.findOneUserByPhoneNum(phoneNumber);
        return new LoginResponseDto(user,cernum);
    }
    @Transactional
    public User join(UserJoinDTO userJoinDTO) {

        if (isDupPhone(userJoinDTO.getPhoneNumber())) {
            throw new RuntimeException("Dup phone number");
        }
        loginRepository.saveUserJoinDto(userJoinDTO);
        User user = loginRepository.findOneUserByPhoneNum(userJoinDTO.getPhoneNumber());
        return user;
    }

    public List<UserDTO> findUserWithIn10KM(DetectiveBoardDTO detectBoardDTO) {
        List<Object[]> getDataInDB = userRepository.findUsersIn10KM(detectBoardDTO.getMissingLatitude(), detectBoardDTO.getMissingLongitude());

        List<UserDTO> foundIn10KM = userRepository.createUserDTOFromObject(getDataInDB);

        return foundIn10KM;
    }
    @Transactional
    public void delete(String phoneNumber){
        loginRepository.deleteByPhone(phoneNumber);
    }


}
