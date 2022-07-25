package com.iospring.pets.petsfinder.user.service;

import com.iospring.pets.petsfinder.detectiveBoard.dto.DetectiveBoardDTO;
import com.iospring.pets.petsfinder.exception.CustomException;
import com.iospring.pets.petsfinder.exception.ErrorCode;
import com.iospring.pets.petsfinder.finderBoard.dto.FinderBoardDTO;
import com.iospring.pets.petsfinder.user.dto.*;
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

    // 새롭게 의뢰 글을 쓴 사람의 글에 대한 알람을 보내주기위해
    // 주변 3km 유저들 (조건x) 을 찾는 서비스
    // 즉, 의뢰글(detective) 작성시 알람을 보낼때 사용되는 것이다.
    public List<DetectUserAlarmDto> findUsersIn3KmWhenUploadDetectiveBoard(DetectiveBoardDTO detectBoardDTO) {
        List<DetectUserAlarmDto> foundIn3KM = userRepository.findUsersIn3KM2(detectBoardDTO.getMissingLatitude(), detectBoardDTO.getMissingLongitude());

        return foundIn3KM;
    }
    //목격한사람이 글작성시 의뢰 한사람들중 해당 목격 정보와 일치하는 게시글작성자들을 다 뽑아오는 서비스
    public List<FindUserAlarmDto> findUsersIn3KmWhenUploadFinderBoard(FinderBoardDTO finderBoardDTO, String breed , String color, String missingTime) {
        List<FindUserAlarmDto> foundIn3KM = userRepository.findUsersIn3KmWhenUploadFinderBoard2(finderBoardDTO.getMissingLatitude(), finderBoardDTO.getMissingLongitude(), breed, color,missingTime);

        return foundIn3KM;
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
