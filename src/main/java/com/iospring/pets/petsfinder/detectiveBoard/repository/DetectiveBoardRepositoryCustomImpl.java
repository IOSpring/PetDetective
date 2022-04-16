package com.iospring.pets.petsfinder.detectiveBoard.repository;


import com.iospring.pets.petsfinder.detectiveBoard.dto.DetectiveBoardDTO;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class DetectiveBoardRepositoryCustomImpl implements DetectiveBoardRepositoryCustom {

    private final EntityManager em;
    public static final int SHOW_DETECTIVE_BOARD_COUNT = 10;

    private List<DetectiveBoardDTO> createDetectBoardDTO(List<Object[]> list) {
        List<DetectiveBoardDTO> resultList = new ArrayList<>();
        for (Object[] objects : list) {
            DetectiveBoardDTO detectBoardDTO = new DetectiveBoardDTO();
            detectBoardDTO.setId((Long) objects[0]);
            detectBoardDTO.setMissingLocation((String) objects[1]);
            detectBoardDTO.setMoney((Integer) objects[2]);
            detectBoardDTO.setMainImageUrl((String) objects[3]);
            detectBoardDTO.setMissingLatitude((Double) objects[4]);
            detectBoardDTO.setMissingLongitude((Double) objects[5]);
            detectBoardDTO.setMissingTime((String) objects[6]);
            resultList.add(detectBoardDTO);
        }
        return resultList;
    }



    @Override
    public List<DetectiveBoardDTO> findAllDetectBoardDTO(int page) {
        List<Object[]> list = em.createQuery("select d.id , d.missLocation , d.money, i.url, d.missingLatitude , d.missingLongitude , d.missingTime" +
                        " from DetectiveBoard d join d.pet p join p.image i order by d.createAt desc ")
                .setFirstResult((page - 1) * SHOW_DETECTIVE_BOARD_COUNT)
                .setMaxResults(SHOW_DETECTIVE_BOARD_COUNT)
                .getResultList();
        return createDetectBoardDTO(list);
    }
}
