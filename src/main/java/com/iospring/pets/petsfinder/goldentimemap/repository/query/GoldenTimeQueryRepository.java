package com.iospring.pets.petsfinder.goldentimemap.repository.query;

import com.iospring.pets.petsfinder.detectiveBoard.entity.DetectiveBoard;
import com.iospring.pets.petsfinder.finderBoard.entity.FinderBoard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class GoldenTimeQueryRepository {
    private final EntityManager em;

    public List<FinderBoard> finderBoards() {
        return em.createQuery(
                "select fb from FinderBoard fb")
                .getResultList();
    }
    public List<DetectiveBoard> detectiveBoards() {
        return em.createQuery(
                "select db from DetectiveBoard db")
                .getResultList();
    }
}
