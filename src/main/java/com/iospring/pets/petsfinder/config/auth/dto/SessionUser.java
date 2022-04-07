package com.iospring.pets.petsfinder.config.auth.dto;

import com.iospring.pets.petsfinder.user.entity.AuthUser;
import com.iospring.pets.petsfinder.user.entity.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(AuthUser user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
