package com.iospring.pets.petsfinder.user.dto;

import lombok.Data;

@Data
public class DetectUserAlarmDto {
    private String phoneNumber;
    private String deviceToken;
    private Long distance;

}
