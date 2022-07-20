package com.iospring.pets.petsfinder.user.dto;

import lombok.Data;

@Data
public class UserAlarmDto {
    private String phone_number;
    private String device_token;
    private String missingTime;
}
