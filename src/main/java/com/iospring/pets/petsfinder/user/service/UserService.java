package com.iospring.pets.petsfinder.user.service;

import com.iospring.pets.petsfinder.detectBoard.dto.DetectBoardDTO;
import com.iospring.pets.petsfinder.user.dto.UserDTO;
import com.iospring.pets.petsfinder.user.dto.UserJoinDTO;
import com.iospring.pets.petsfinder.user.entity.User;
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


    private boolean isDupPhone(String phoneNumber) {
        User user = userRepository.findByPhoneNumber(phoneNumber).orElse(null);

        return user == null ? false : true;
    }

    @Transactional
    public User join(UserJoinDTO userJoinDTO) {

        if (isDupPhone(userJoinDTO.getPhoneNumber())) {
            throw new RuntimeException("Dup phone number");
        }

        User user = new User();
        user.setEmail(userJoinDTO.getEmail());
        user.setAlterCount(0);
        user.setPhoneNumber(userJoinDTO.getPhoneNumber());
        user.setLatitude(userJoinDTO.getLatitude());
        user.setLongitude(userJoinDTO.getLongitude());
        user.setLoadAddress(userJoinDTO.getLoadAddress());

        userRepository.save(user);

        return user;
    }

    public List<UserDTO> findUserWithIn10KM(DetectBoardDTO detectBoardDTO) {
        List<Object[]> getDataInDB = userRepository.findUsersIn10KM(detectBoardDTO.getMissingLatitude(), detectBoardDTO.getMissingLongitude());

        List<UserDTO> foundIn10KM = userRepository.createUserDTOFromObject(getDataInDB);

        return foundIn10KM;
    }

}
