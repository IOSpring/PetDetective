package com.iospring.pets.petsfinder.user.dto;

import com.iospring.pets.petsfinder.user.entity.User;
import lombok.Data;

@Data
public class UserDTO {


    private String missingTime;
    private Long id;
    private String phoneNumber;
    private Double latitude;
    private Double longitude;
    private String deviceToken;
}
