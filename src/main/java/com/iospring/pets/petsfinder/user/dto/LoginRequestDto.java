package com.iospring.pets.petsfinder.user.dto;


import lombok.Data;

@Data
public class LoginRequestDto {
    String phoneNumber;
    String deviceToken;
}
