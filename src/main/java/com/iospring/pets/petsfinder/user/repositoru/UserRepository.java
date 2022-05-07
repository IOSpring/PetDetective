package com.iospring.pets.petsfinder.user.repositoru;

import com.iospring.pets.petsfinder.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, CustomUserRepository {

    Optional<User> findByPhoneNumber(String phoneNumber);

    @Query(value = "SELECT user_id, phone_number,latitude, longitude,device_token,(6371*ACOS(COS(RADIANS(:latitude))*COS(RADIANS(latitude))*COS(RADIANS(longitude)-RADIANS(:longitude))\n" +
            "+SIN(RADIANS(:latitude))*SIN(RADIANS(latitude)))) AS distance FROM user HAVING distance < 10 ORDER BY distance DESC", nativeQuery = true)
    List<Object[]> findUsersIn10KM(double latitude, double longitude);

    @Query(value = "SELECT user_id, phone_number,latitude, longitude, device_token ,(6371*ACOS(COS(RADIANS(:latitude))*COS(RADIANS(latitude))*COS(RADIANS(longitude)-RADIANS(:longitude))\n" +
            "            +SIN(RADIANS(:latitude))*SIN(RADIANS(latitude)))) AS distance, i.breed , i.color\n" +
            "FROM user\n" +
            "    join detective_board db on user.user_id = db.d_board_user_fk\n" +
            "    join pet p on p.pet_id = db.d_board_pet_fk\n" +
            "    join image i on i.image_id = p.pet_image_fk where i.color = :color and i.breed = :breed " +
            "HAVING distance < 3  ORDER BY distance DESC", nativeQuery = true)
    List<Object[]> findUsersIn3KmWhenUploadFinderBoard(double latitude, double longitude, String breed, String color);





    @Query(value = "SELECT user_id, phone_number,latitude, longitude,device_token,(6371*ACOS(COS(RADIANS(:latitude))*COS(RADIANS(latitude))*COS(RADIANS(longitude)-RADIANS(:longitude))\n" +
            "            +SIN(RADIANS(:latitude))*SIN(RADIANS(latitude)))) AS distance, i.breed , i.color\n" +
            "FROM user\n" +
            "    join finder_board fb on user.user_id = fb.f_board_user_fk\n" +
            "    join pet p on p.pet_id = fb.f_board_pet_fk\n" +
            "    join image i on i.image_id = p.pet_image_fk where i.color = :color and i.breed = :breed " +
            "HAVING distance < 3  ORDER BY distance DESC", nativeQuery = true)
    List<Object[]> findUsersIn3KmWhenUploadDetectiveBoard(double latitude, double longitude, String breed, String color);
}
