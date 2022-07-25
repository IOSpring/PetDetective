package com.iospring.pets.petsfinder.user.repository;

import com.iospring.pets.petsfinder.user.Mapper.DetectUserAlarmMapper;
import com.iospring.pets.petsfinder.user.Mapper.FindUserAlarmMapper;
import com.iospring.pets.petsfinder.user.dto.DetectUserAlarmDto;
import com.iospring.pets.petsfinder.user.dto.FindUserAlarmDto;
import com.iospring.pets.petsfinder.user.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.persistence.EntityManager;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CustomUserRepositoryImpl implements CustomUserRepository{

    private final EntityManager em;
    private final JdbcTemplate jdbcTemplate;

// 삭제 대기
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

    @Override
    public List<DetectUserAlarmDto> findUsersIn3KM2(double latitude, double longitude) {
        String SQL = "SELECT phone_number, device_token,(6371*ACOS(COS(RADIANS(?))*COS(RADIANS(latitude))*COS(RADIANS(longitude)-RADIANS(?))+SIN(RADIANS(?))*SIN(RADIANS(latitude)))) AS distance FROM user  HAVING distance < 3 ORDER BY distance DESC";
        List<DetectUserAlarmDto> detectUserAlarmDtoList =jdbcTemplate.query(SQL,new DetectUserAlarmMapper(),latitude,longitude,latitude);

        return detectUserAlarmDtoList;
    }

    @Override
    public List<FindUserAlarmDto> findUsersIn3KmWhenUploadFinderBoard2(double latitude, double longitude, String breed, String color, String missingTime) {
        String SQL = "SELECT  phone_number, device_token ,db.missing_time,(6371*ACOS(COS(RADIANS(:latitude))*COS(RADIANS(db.missing_latitude))*COS(RADIANS(db.missing_longitude)-RADIANS(:longitude))\n" +
                "            +SIN(RADIANS(:latitude))*SIN(RADIANS(db.missing_latitude)))) AS distance\n" +
                "FROM user\n" +
                "    join detective_board db on user.user_id = db.d_board_user_fk\n" +
                "    join pet p on p.pet_id = db.d_board_pet_fk\n" +
                "    join image i on i.image_id = p.pet_image_fk where i.color = :color and i.breed = :breed and db.missing_time <= :missingTime " +
                "HAVING distance < 3  ORDER BY distance DESC";
        List<FindUserAlarmDto> findUserAlarmDtoList =jdbcTemplate.query(SQL,new FindUserAlarmMapper());
        return findUserAlarmDtoList;
    }
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
