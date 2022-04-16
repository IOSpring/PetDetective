package com.iospring.pets.petsfinder.user.repositoru;

import com.iospring.pets.petsfinder.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> , CustomUserRepository{

    Optional<User> findByPhoneNumber(String phoneNumber);


    @Query(value = "SELECT user_id, phone_number,latitude, longitude,(6371*ACOS(COS(RADIANS(:latitude))*COS(RADIANS(latitude))*COS(RADIANS(longitude)-RADIANS(:longitude))\n" +
            "+SIN(RADIANS(:latitude))*SIN(RADIANS(latitude)))) AS distance FROM user HAVING distance < 10 ORDER BY distance DESC", nativeQuery = true)
    List<Object[]> findUsersIn10KM(double latitude, double longitude);


}
