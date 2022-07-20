package com.iospring.pets.petsfinder.user.repository;

import com.iospring.pets.petsfinder.user.dto.UserAlarmDto;
import com.iospring.pets.petsfinder.user.dto.UserDTO;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CustomUserRepositoryImpl implements CustomUserRepository{

    private final EntityManager em;

    @Override
    public List<UserAlarmDto> findUsersIn3KM2(double latitude, double longitude) {
        List<UserAlarmDto> resultList = em.createQuery("SELECT phone_number as phoneNumber, device_token as deviceToken,(6371*ACOS(COS(RADIANS(:latitude))*COS(RADIANS(latitude))*COS(RADIANS(longitude)-RADIANS(:longitude))\n" +
                "+SIN(RADIANS(:latitude))*SIN(RADIANS(latitude)))) AS distance FROM user  HAVING distance < 3 ORDER BY distance DESC", UserAlarmDto.class)
                .setParameter("latitude", latitude)
                .setParameter("longitude", longitude)
                .getResultList();
        return resultList;
    }
// 삭제 대기
//     public List<UserDTO> createUserDTOFromObjectForFindBoard(List<Object[]> list) {
//        List<UserDTO> userDTOList = new ArrayList<>();
//        for (Object[] objects : list) {
//            BigInteger bigInteger = new BigInteger(String.valueOf(objects[0]));
//            UserDTO userDTO = new UserDTO();
//            userDTO.setId(bigInteger.longValue());
//            userDTO.setPhoneNumber((String) objects[1]);
//            userDTO.setLatitude((Double) objects[2]);
//            userDTO.setLongitude((Double) objects[3]);
//            userDTO.setDeviceToken((String) objects[4]);
//            userDTO.setMissingTime((String) objects[5]);
//            userDTOList.add(userDTO);
//        }
//
//        return userDTOList;
//    }
//    public List<UserDTO> createUserDTOFromObjectForDetectBoard(List<Object[]> list) {
//
//        List<UserDTO> userDTOList = new ArrayList<>();
//
//        for (Object[] objects : list) {
//            BigInteger bigInteger = new BigInteger(String.valueOf(objects[0]));
//            UserDTO userDTO = new UserDTO();
//            userDTO.setId(bigInteger.longValue());
//            userDTO.setPhoneNumber((String) objects[1]);
//            userDTO.setLatitude((Double) objects[2]);
//            userDTO.setLongitude((Double) objects[3]);
//            userDTO.setDeviceToken((String) objects[4]);
//            userDTOList.add(userDTO);
//        }
//
//        return userDTOList;
//    }

}
