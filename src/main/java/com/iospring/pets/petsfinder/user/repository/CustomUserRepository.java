package com.iospring.pets.petsfinder.user.repository;

import com.iospring.pets.petsfinder.user.dto.UserDTO;

import java.util.List;

public interface CustomUserRepository {

      List<UserDTO> createUserDTOFromObjectForFindeBoard(List<Object[]> list);
      List<UserDTO> createUserDTOFromObjectForDetectBoard(List<Object[]> list);



}
