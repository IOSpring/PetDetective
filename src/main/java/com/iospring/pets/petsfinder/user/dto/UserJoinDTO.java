package com.iospring.pets.petsfinder.user.dto;

import lombok.Data;

@Data
public class UserJoinDTO {
    private String phoneNumber;
    private String email;
    private String loadAddress;
    private Double latitude;
    private Double longitude;
    private String deviceToken;
}
