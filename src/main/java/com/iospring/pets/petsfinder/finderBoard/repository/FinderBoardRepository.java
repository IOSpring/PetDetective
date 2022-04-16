package com.iospring.pets.petsfinder.finderBoard.repository;

import com.iospring.pets.petsfinder.finderBoard.entity.FinderBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FinderBoardRepository  extends JpaRepository<FinderBoard, Long>, CustomFinderBoardRepository {
    @Query("SELECT COUNT(f) FROM FinderBoard f where f.isCare = true")
    long getIsCareCount();

    @Query("SELECT COUNT(f) FROM FinderBoard f where f.isCare = false")
    long getIsNotCareCount();
}
