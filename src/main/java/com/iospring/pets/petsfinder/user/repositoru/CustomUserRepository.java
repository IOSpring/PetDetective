package com.iospring.pets.petsfinder.user.repositoru;

import com.iospring.pets.petsfinder.user.dto.UserDTO;
import com.iospring.pets.petsfinder.user.entity.User;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomUserRepository {
     List<UserDTO> createUserDTOFromObject(List<Object[]> list);

}
