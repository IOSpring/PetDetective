package com.iospring.pets.petsfinder.user.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class AuthUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public AuthUser(String name, String email, String picture, Role role) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }


    public String getRoleKey() {
        return this.role.getKey();
    }

    public AuthUser update(String name, String picture) {
        this.name = name;
        this.picture = picture;
        return this;
    }
}
