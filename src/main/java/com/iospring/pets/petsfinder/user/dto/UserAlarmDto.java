package com.iospring.pets.petsfinder.user.dto;

import lombok.Data;

@Data
public class UserAlarmDto {
    private String phoneNumber;
    private String deviceToken;
    private String missingTime;
    private Long distance;
    public UserAlarmDto(String phoneNumber, String deviceToken, String missingTime, Long distance) {
        this.phoneNumber = phoneNumber;
        this.deviceToken = deviceToken;
        this.missingTime = missingTime;
        this.distance = distance;
    }
}
