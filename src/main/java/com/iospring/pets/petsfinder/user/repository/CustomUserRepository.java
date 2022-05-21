package com.iospring.pets.petsfinder.user.repository;

import com.iospring.pets.petsfinder.user.dto.UserDTO;

import java.util.List;

public interface CustomUserRepository {

      List<UserDTO> createUserDTOFromObject(List<Object[]> list);



}
