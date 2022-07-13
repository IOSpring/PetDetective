package com.iospring.pets.petsfinder.user.repository;

import com.iospring.pets.petsfinder.user.dto.UserDTO;
import lombok.RequiredArgsConstructor;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CustomUserRepositoryImpl implements CustomUserRepository{

     public List<UserDTO> createUserDTOFromObjectForFindBoard(List<Object[]> list) {

        List<UserDTO> userDTOList = new ArrayList<>();

        for (Object[] objects : list) {
            BigInteger bigInteger = new BigInteger(String.valueOf(objects[0]));
            UserDTO userDTO = new UserDTO();
            userDTO.setId(bigInteger.longValue());
            userDTO.setPhoneNumber((String) objects[1]);
            userDTO.setLatitude((Double) objects[2]);
            userDTO.setLongitude((Double) objects[3]);
            userDTO.setDeviceToken((String) objects[4]);
            userDTO.setMissingTime((String) objects[5]);
            userDTOList.add(userDTO);
        }

        return userDTOList;
    }
    public List<UserDTO> createUserDTOFromObjectForDetectBoard(List<Object[]> list) {

        List<UserDTO> userDTOList = new ArrayList<>();

        for (Object[] objects : list) {
            BigInteger bigInteger = new BigInteger(String.valueOf(objects[0]));
            UserDTO userDTO = new UserDTO();
            userDTO.setId(bigInteger.longValue());
            userDTO.setPhoneNumber((String) objects[1]);
            userDTO.setLatitude((Double) objects[2]);
            userDTO.setLongitude((Double) objects[3]);
            userDTO.setDeviceToken((String) objects[4]);
            userDTOList.add(userDTO);
        }

        return userDTOList;
    }

}
