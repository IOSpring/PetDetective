package com.iospring.pets.petsfinder.user.repositoru;

import com.iospring.pets.petsfinder.user.dto.LoginResponseDto;
import com.iospring.pets.petsfinder.user.dto.UserDTO;
import com.iospring.pets.petsfinder.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.parameters.P;

import javax.persistence.EntityManager;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CustomUserRepositoryImpl implements CustomUserRepository{

     public List<UserDTO> createUserDTOFromObject(List<Object[]> list) {

        List<UserDTO> userDTOList = new ArrayList<>();

        for (Object[] objects : list) {
            BigInteger bigInteger = new BigInteger(String.valueOf(objects[0]));
            UserDTO userDTO = new UserDTO();
            userDTO.setId(bigInteger.longValue());
            userDTO.setPhoneNumber((String) objects[1]);
            userDTO.setLatitude((Double) objects[2]);
            userDTO.setLongitude((Double) objects[3]);
            userDTOList.add(userDTO);
        }

        return userDTOList;
    }

}
