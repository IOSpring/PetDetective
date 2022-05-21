package com.iospring.pets.petsfinder.user.repository;

import com.iospring.pets.petsfinder.exception.CustomException;
import com.iospring.pets.petsfinder.exception.ErrorCode;
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
    private final UserRepository userRepository;
    @Transactional
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
        user.setLatitude(Double.parseDouble(userJoinDTO.getLatitude()));
        user.setLongitude(Double.parseDouble(userJoinDTO.getLongitude()));
        user.setLoadAddress(userJoinDTO.getLoadAddress());
        user.setDeviceToken(userJoinDTO.getDeviceToken());


        em.persist(user);
    }

    @Transactional
    public void updateDeviceToken(String phoneNumber, String diviceToken) {
        User user=findOneUserByPhoneNum(phoneNumber);

        if(user !=null) {
            user.setDeviceToken(diviceToken);
            em.persist(user);
        }
    }
    @Transactional
    public void updateLocationByPhoneNumber(String PhoneNumber, Double latitude,Double longitude,String loadAddress){
        /*User user=findOneUserByPhoneNum(PhoneNumber);
        System.out.println("user.getPhoneNumber() = " + user.getPhoneNumber());

*/
        System.out.println("longitude = " + longitude);
        System.out.println("latitude = " + latitude);
        System.out.println("loadAddress = " + loadAddress);
        User user = userRepository.findByPhoneNumber(PhoneNumber)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        
        user.setLatitude(latitude);
        user.setLongitude(longitude);
        user.setLoadAddress(loadAddress);

        System.out.println("user.getLatitude() = " + user.getLatitude());
        System.out.println("user.getLongitude() = " + user.getLongitude());
        System.out.println("user.getLoadAddress() = " + user.getLoadAddress());
//        userRepository.save(user);
    }


    public void deleteByPhone(String phoneNumber) {
        em.createQuery(
                "delete from User u where u.phoneNumber = :phoneNumber")
                .setParameter("phoneNumber",phoneNumber)
                .executeUpdate();
    }
}
