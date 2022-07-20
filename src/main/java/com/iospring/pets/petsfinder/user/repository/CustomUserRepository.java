package com.iospring.pets.petsfinder.user.repository;

import com.iospring.pets.petsfinder.user.dto.UserAlarmDto;
import com.iospring.pets.petsfinder.user.dto.UserDTO;

import java.util.List;

public interface CustomUserRepository {
      //삭제 대기
//      List<UserDTO> createUserDTOFromObjectForFindBoard(List<Object[]> list);
//      List<UserDTO> createUserDTOFromObjectForDetectBoard(List<Object[]> list);
      List<UserAlarmDto> findUsersIn3KM2(double latitude, double longitude);
}
