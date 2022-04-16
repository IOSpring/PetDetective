package com.iospring.pets.petsfinder.user.repositoru;

import com.iospring.pets.petsfinder.user.dto.UserDTO;

import java.util.List;

public interface CustomUserRepository {

      List<UserDTO> createUserDTOFromObject(List<Object[]> list);



}
