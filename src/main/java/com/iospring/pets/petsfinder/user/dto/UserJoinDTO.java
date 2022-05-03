package com.iospring.pets.petsfinder.user.dto;

import lombok.Data;

@Data
public class UserJoinDTO {
    private String phoneNumber;
    private String email;
    private String loadAddress;
    private String latitude;
    private String longitude;
    private String deviceToken;
}
