package com.iospring.pets.petsfinder.user.repository;

import com.iospring.pets.petsfinder.user.dto.DetectUserAlarmDto;
import com.iospring.pets.petsfinder.user.dto.FindUserAlarmDto;

import java.util.List;

public interface CustomUserRepository {
      //삭제 대기
//      List<UserDTO> createUserDTOFromObjectForFindBoard(List<Object[]> list);
//      List<UserDTO> createUserDTOFromObjectForDetectBoard(List<Object[]> list);
    List<FindUserAlarmDto> findUsersIn3KM2(double latitude, double longitude);
    List<DetectUserAlarmDto> findUsersIn3KmWhenUploadFinderBoard2(double latitude, double longitude, String breed, String color, String missingTime);

}
