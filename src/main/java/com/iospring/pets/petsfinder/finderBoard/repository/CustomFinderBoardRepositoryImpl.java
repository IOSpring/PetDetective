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
            finderBoardDTO.setUserPhoneNumber((String) objects[7]);
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
            userDTO.setDeviceToken((String) objects[4]);

            userDTOList.add(userDTO);
        }

        return userDTOList;
    }
    private final EntityManager em;

    @Override
    public List<UserDTO> findUserListMatchingBreedAndColor(String breed, String color) {

        List<Object[]> resultListObj = em.createQuery("select u.id, u.phoneNumber,u.latitude, u.longitude , u.deviceToken from DetectiveBoard db inner join db.user u inner join db.pet p inner  join p.image i where i.color = :color and i.breed = :breed")
                .setParameter("breed", breed)
                .setParameter("color", color)
                .getResultList();

        List<UserDTO> userDTOList = createUserDTOFromObject(resultListObj);

        return userDTOList;

    }



    @Override
    public List<FinderBoardDTO> getCareFinderBoardDTO(int page) {
        List<Object[]> list = em.createQuery("select f.id , f.missLocation , f.isCare, i.url, f.missingLatitude , f.missingLongitude , f.missingTime, u.phoneNumber" +
                        " from FinderBoard f join f.pet p join p.image i join f.user u where f.isCare = :isCare order by f.createAt desc ")
                .setParameter("isCare", true)
                .setFirstResult((page - 1) * SHOW_FINDER_BOARD_COUNT)
                .setMaxResults(SHOW_FINDER_BOARD_COUNT)
                .getResultList();
        return createFindBoardDTO(list);
    }

    @Override
    public List<FinderBoardDTO> getNotCareFinderBoardDTO(int page) {
        List<Object[]> list = em.createQuery("select f.id , f.missLocation , f.isCare, i.url, f.missingLatitude , f.missingLongitude , f.missingTime, u.phoneNumber" +
                        " from FinderBoard f join f.pet p join p.image i join f.user u where f.isCare = :isCare order by f.createAt desc")
                .setParameter("isCare", false)
                .setFirstResult((page - 1) * SHOW_FINDER_BOARD_COUNT)
                .setMaxResults(SHOW_FINDER_BOARD_COUNT)
                .getResultList();

        return createFindBoardDTO(list);

    }

    @Override
    public List<FinderBoardDTO> getAllFinderBoardDTO(int page) {
        List<Object[]> list = em.createQuery("select f.id , f.missLocation , f.isCare, i.url, f.missingLatitude , f.missingLongitude , f.missingTime, u.phoneNumber" +
                        " from FinderBoard f join f.pet p join p.image i join f.user u order by f.createAt desc")
                .setFirstResult((page - 1) * SHOW_FINDER_BOARD_COUNT)
                .setMaxResults(SHOW_FINDER_BOARD_COUNT)
                .getResultList();

        return createFindBoardDTO(list);
    }

    @Override
    public List<FinderBoardDTO> findFinderBoardDtoByLocation(int page, String condition) {
        List<Object[]> list = em.createQuery("select f.id , f.missLocation, f.isCare , i.url, f.missingLatitude , f.missingLongitude , f.missingTime, u.phoneNumber" +
                        " from FinderBoard f join f.pet p join p.image i join f.user u where  f.missLocation like :condition order by f.createAt desc ")
                .setParameter("condition" , '%' + condition +'%')
                .setFirstResult((page - 1) * SHOW_FINDER_BOARD_COUNT)
                .setMaxResults(SHOW_FINDER_BOARD_COUNT)
                .getResultList();
        return createFindBoardDTO(list);
    }



    @Override
    public long countFinderBoardDtoSearchedByLocation(String condition) {
        return em.createQuery("select count(f.missLocation) from FinderBoard f where f.missLocation  like :condition", Long.class)
                .setParameter("condition" , '%' + condition +'%')
                .getSingleResult();
    }

    @Override
    public List<FinderBoardDTO> findFinderBoardDtoByBreed(int page, String condition) {
        List<Object[]> list = em.createQuery("select f.id , f.missLocation, f.isCare , i.url, f.missingLatitude , f.missingLongitude , f.missingTime, u.phoneNumber" +
                        " from FinderBoard f join f.pet p join p.image i join f.user u where i.breed like :condition order by f.createAt desc ")
                .setParameter("condition" , '%' + condition +'%')
                .setFirstResult((page - 1) * SHOW_FINDER_BOARD_COUNT)
                .setMaxResults(SHOW_FINDER_BOARD_COUNT)
                .getResultList();
        return createFindBoardDTO(list);
    }

    @Override
    public long countDetectBoardDtoSearchedByBreed(String condition) {

        return em.createQuery("select count(i.breed) from FinderBoard f join f.pet p join p.image i where i.breed like :condition", Long.class)
                .setParameter("condition" , '%' +  condition + '%')
                .getSingleResult();
    }

    @Override
    public List<FinderBoardDTO> findFinderBoardDtoByColor(int page, String condition) {
        List<Object[]> list = em.createQuery("select f.id , f.missLocation, f.isCare , i.url, f.missingLatitude , f.missingLongitude , f.missingTime, u.phoneNumber" +
                        " from FinderBoard f join f.pet p join p.image i join f.user u  where i.color like :condition order by f.createAt desc ")
                .setParameter("condition" , '%' + condition +'%')
                .setFirstResult((page - 1) * SHOW_FINDER_BOARD_COUNT)
                .setMaxResults(SHOW_FINDER_BOARD_COUNT)
                .getResultList();

        return createFindBoardDTO(list);

    }

    @Override
    public long countFinderBoardDtoSearchedByColor(String condition) {
        return em.createQuery("select count(i.breed) from FinderBoard f join f.pet p join p.image i where i.color like :condition", Long.class)
                .setParameter("condition" , '%' +  condition + '%')
                .getSingleResult();
    }

}



