package com.iospring.pets.petsfinder.user.dto;

import lombok.Data;

@Data

public class FindUserAlarmDto {
    private String phoneNumber;
    private String deviceToken;
    private String missingTime;
    private Long distance;

}
