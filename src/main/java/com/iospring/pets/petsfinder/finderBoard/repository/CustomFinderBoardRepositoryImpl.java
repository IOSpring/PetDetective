package com.iospring.pets.petsfinder.finderBoard.repository;

import com.iospring.pets.petsfinder.finderBoard.dto.FinderBoardDTO;
import com.iospring.pets.petsfinder.user.dto.UserDTO;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
public class CustomFinderBoardRepositoryImpl implements CustomFinderBoardRepository {

    public static final int SHOW_FINDER_BOARD_COUNT = 10;

    private List<FinderBoardDTO> createFindBoardDTO(List<Object[]> list) {
        List<FinderBoardDTO> resultList = new ArrayList<>();
        for (Object[] objects : list) {
            FinderBoardDTO finderBoardDTO = new FinderBoardDTO();
            finderBoardDTO.setId((Long) objects[0]);
            finderBoardDTO.setMissingLocation((String) objects[1]);
            finderBoardDTO.setCare((Boolean) objects[2]);
            finderBoardDTO.setMainImageUrl((String) objects[3]);
            finderBoardDTO.setMissingLatitude((Double) objects[4]);
            finderBoardDTO.setMissingLongitude((Double) objects[5]);
            finderBoardDTO.setMissingTime((String) objects[6]);
            resultList.add(finderBoardDTO);
        }
        return resultList;
    }

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



    @Override
    public List<FinderBoardDTO> getCareFinderBoardDTO(int page) {
        List<Object[]> list = em.createQuery("select f.id , f.missLocation , f.isCare, i.url, f.missingLatitude , f.missingLongitude , f.missingTime" +
                        " from FinderBoard f join f.pet p join p.image i where f.isCare = :isCare order by f.createAt desc ")
                .setParameter("isCare", true)
                .setFirstResult((page - 1) * SHOW_FINDER_BOARD_COUNT)
                .setMaxResults(SHOW_FINDER_BOARD_COUNT)
                .getResultList();
        return createFindBoardDTO(list);
    }

    @Override
    public List<FinderBoardDTO> getNotCareFinderBoardDTO(int page) {
        List<Object[]> list = em.createQuery("select f.id , f.missLocation , f.isCare, i.url, f.missingLatitude , f.missingLongitude , f.missingTime" +
                        " from FinderBoard f join f.pet p join p.image i where f.isCare = :isCare order by f.createAt desc")
                .setParameter("isCare", false)
                .setFirstResult((page - 1) * SHOW_FINDER_BOARD_COUNT)
                .setMaxResults(SHOW_FINDER_BOARD_COUNT)
                .getResultList();

        return createFindBoardDTO(list);

    }

    @Override
    public List<FinderBoardDTO> getAllFinderBoardDTO(int page) {
        List<Object[]> list = em.createQuery("select f.id , f.missLocation , f.isCare, i.url, f.missingLatitude , f.missingLongitude , f.missingTime" +
                        " from FinderBoard f join f.pet p join p.image i order by f.createAt desc")
                .setFirstResult((page - 1) * SHOW_FINDER_BOARD_COUNT)
                .setMaxResults(SHOW_FINDER_BOARD_COUNT)
                .getResultList();

        return createFindBoardDTO(list);
    }

}



