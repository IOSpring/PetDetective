package com.iospring.pets.petsfinder.finderBoard.repository;

import com.iospring.pets.petsfinder.user.dto.UserDTO;
import com.iospring.pets.petsfinder.user.entity.User;
import com.iospring.pets.petsfinder.user.repositoru.UserRepository;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
public class CustomFinderBoardRepositoryImpl implements CustomFinderBoardRepository {

    public List<UserDTO> createUserDTOFromObject(List<Object[]> list) {

        List<UserDTO> userDTOList = new ArrayList<>();

        for (Object[] objects : list) {
            BigInteger bigInteger = new BigInteger(String.valueOf(objects[0]));
            UserDTO userDTO = new UserDTO();
            userDTO.setId(bigInteger.longValue());
            userDTO.setPhoneNumber((String) objects[1]);
            userDTO.setLatitude((Double) objects[2]);
            userDTO.setLongitude((Double) objects[3]);
            userDTOList.add(userDTO);
        }

        return userDTOList;
    }
    private final EntityManager em;

    @Override
    public List<UserDTO> findUserListMatchingBreedAndColor(String breed, String color) {

        List<Object[]> resultListObj = em.createQuery("select u.id, u.phoneNumber,u.latitude, u.longitude from DetectiveBoard db inner join db.user u inner join db.pet p inner  join p.image i where i.color = :color and i.breed = :breed")
                .setParameter("breed", breed)
                .setParameter("color", color)
                .getResultList();

        List<UserDTO> userDTOList = createUserDTOFromObject(resultListObj);

        return userDTOList;
    }
}
