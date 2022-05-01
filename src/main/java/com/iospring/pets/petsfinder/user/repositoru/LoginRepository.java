package com.iospring.pets.petsfinder.user.repositoru;

import com.iospring.pets.petsfinder.user.dto.UserJoinDTO;
import com.iospring.pets.petsfinder.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginRepository {

    private final EntityManager em;
    public User findOneUserByPhoneNum (String phoneNumber){
        List<User> user = em.createQuery("select u from User u where u.phoneNumber = :phoneNumber", User.class)
                .setParameter("phoneNumber", phoneNumber)
                .getResultList();
        if(user.size()==0)
            return null;
        return user.get(0);
    }

    @Transactional
    public void saveUserJoinDto(UserJoinDTO userJoinDTO){


        User user = new User();
        user.setEmail(userJoinDTO.getEmail());
        user.setAlterCount(0);
        user.setPhoneNumber(userJoinDTO.getPhoneNumber());
        user.setLatitude(userJoinDTO.getLatitude());
        user.setLongitude(userJoinDTO.getLongitude());
        user.setLoadAddress(userJoinDTO.getLoadAddress());
        user.setDeviceToken(userJoinDTO.getDeviceToken());

        em.persist(user);
    }
}
