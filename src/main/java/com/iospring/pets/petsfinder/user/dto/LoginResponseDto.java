package com.iospring.pets.petsfinder.user.dto;

import com.iospring.pets.petsfinder.user.entity.User;
import lombok.Data;

@Data
public class LoginResponseDto {

    private boolean needjoin;
    private String cernum;

    public LoginResponseDto(User user, String cernum) {
        this.needjoin = user==null ? true : false;
        this.cernum = cernum;
    }

}
