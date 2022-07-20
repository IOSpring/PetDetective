package com.iospring.pets.petsfinder.user.dto;

import lombok.Data;

@Data
public class UserAlarmDto {
    private String phoneNumber;
    private String deviceToken;
    private String missingTime;
}
