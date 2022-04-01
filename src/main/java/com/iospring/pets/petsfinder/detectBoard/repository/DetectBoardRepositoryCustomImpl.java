package com.iospring.pets.petsfinder.detectBoard.repository;


import com.iospring.pets.petsfinder.detectBoard.dto.DetectBoardDTO;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class DetectBoardRepositoryCustomImpl implements DetectBoardRepositoryCustom{

    private final EntityManager em;
    private final int SHOW_BOARD_COUNT = 3;
    private List<DetectBoardDTO> createDetectBoardDTO(List<Object[]> list) {
        List<DetectBoardDTO> resultList = new ArrayList<>();
        for (Object[] objects : list) {
            DetectBoardDTO detectBoardDTO = new DetectBoardDTO();
            detectBoardDTO.setId((Long) objects[0]);
            detectBoardDTO.setMissingLocation((String) objects[1]);
            detectBoardDTO.setMoney((Integer) objects[2]);
            detectBoardDTO.setMainImageUrl((String) objects[3]);

            resultList.add(detectBoardDTO);
        }

        return resultList;
    }



    @Override
    public List<DetectBoardDTO> findAllDetectBoardDTO(int page) {
        List<Object[]> list = em.createQuery("select d.id , d.missLocation , d.money, i.url from DetectiveBoard d join d.pet p join p.image i")
                .setFirstResult((page - 1) * SHOW_BOARD_COUNT)
                .setMaxResults(SHOW_BOARD_COUNT)
                .getResultList();

        return createDetectBoardDTO(list);

    }
}
