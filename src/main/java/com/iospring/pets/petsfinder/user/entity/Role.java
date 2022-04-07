package com.iospring.pets.petsfinder.user.entity;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    GUEST("ROLE_GUEST", "GUEST"),
    UESR("ROLE_USER", "USER");


    private final String key;
    private final String title;



}
