package com.iospring.pets.petsfinder.user.repositoru;

import com.iospring.pets.petsfinder.user.entity.AuthUser;
import com.iospring.pets.petsfinder.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {
    Optional<AuthUser> findByEmail(String email);
}
