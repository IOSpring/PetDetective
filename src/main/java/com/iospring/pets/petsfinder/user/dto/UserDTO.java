package com.iospring.pets.petsfinder.user.dto;

import com.iospring.pets.petsfinder.user.entity.User;
import lombok.Data;

@Data
public class UserDTO {

    public static UserDTO createUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setLatitude(user.getLatitude());
        userDTO.setLongitude(user.getLongitude());
        userDTO.setDeviceToken(user.getDeviceToken());

        return userDTO;
    }


    private Long id;
    private String phoneNumber;
    private Double latitude;
    private Double longitude;
    private String deviceToken;
}
