package com.iospring.pets.petsfinder.user.service;

import com.iospring.pets.petsfinder.detectiveBoard.dto.DetectiveBoardDTO;
import com.iospring.pets.petsfinder.exception.CustomException;
import com.iospring.pets.petsfinder.exception.ErrorCode;
import com.iospring.pets.petsfinder.finderBoard.dto.FinderBoardDTO;
import com.iospring.pets.petsfinder.user.dto.LoginResponseDto;
import com.iospring.pets.petsfinder.user.dto.UserDTO;
import com.iospring.pets.petsfinder.user.dto.UserJoinDTO;
import com.iospring.pets.petsfinder.user.dto.UserLocationDto;
import com.iospring.pets.petsfinder.user.entity.User;
import com.iospring.pets.petsfinder.user.repository.LoginRepository;
import com.iospring.pets.petsfinder.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
            throw new CustomException(ErrorCode.DUPLICATE_PHONENUMBER);
        }
        loginRepository.saveUserJoinDto(userJoinDTO);
        User user = loginRepository.findOneUserByPhoneNum(userJoinDTO.getPhoneNumber());
        return user;
    }

    public List<UserDTO> findUsersIn3KmWhenUploadDetectiveBoard(DetectiveBoardDTO detectBoardDTO) {
        List<Object[]> getDataInDB = userRepository.findUsersIn3KmWhenUploadDetectiveBoard(detectBoardDTO.getMissingLatitude(), detectBoardDTO.getMissingLongitude());

        List<UserDTO> foundIn10KM = userRepository.createUserDTOFromObject(getDataInDB);

        return foundIn10KM;
    }

    public List<UserDTO> findUsersIn3KmWhenUploadFinderBoard(FinderBoardDTO finderBoardDTO, String breed , String color,String missingTime) {
        List<Object[]> getDataInDB = userRepository.findUsersIn3KmWhenUploadFinderBoard(finderBoardDTO.getMissingLatitude(), finderBoardDTO.getMissingLongitude(), breed, color,missingTime);

        List<UserDTO> foundIn10KM = userRepository.createUserDTOFromObject(getDataInDB);

        return foundIn10KM;
    }
    @Transactional
    public void delete(String phoneNumber){
        loginRepository.deleteByPhone(phoneNumber);
    }


    public User findByUserId(Long userId){
        Optional<User> user=  userRepository.findById(userId);
        return user.orElseThrow(() -> new RuntimeException("사용자 없음"));
    }

    public User findByUserPhoneNumber(String phoneNumber){
        Optional<User> user=  userRepository.findByPhoneNumber(phoneNumber);
        return user.orElseThrow(() -> new RuntimeException("사용자 없음"));
    }
    public void updateLocation(UserLocationDto userDto){
        loginRepository.updateLocationByPhoneNumber(userDto.getPhoneNumber(),userDto.getLatitude(),userDto.getLongitude(),userDto.getLoadAddress());

    }

    @Transactional
    public void updateLocation2(UserLocationDto userLocationDto) {
        User user = userRepository.findByPhoneNumber(userLocationDto.getPhoneNumber())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        user.setLatitude(userLocationDto.getLatitude());
        user.setLongitude(userLocationDto.getLongitude());
        user.setLoadAddress(userLocationDto.getLoadAddress());

    }
}
