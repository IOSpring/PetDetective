package com.iospring.pets.petsfinder.user.dto;


import lombok.Data;

@Data
public class UserLocationDto {
    private String phoneNumber;
    private String loadAddress;
    private Double latitude;
    private Double longitude;
}
