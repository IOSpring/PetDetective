package com.iospring.pets.petsfinder.finderBoard.repository;

import com.iospring.pets.petsfinder.finderBoard.entity.FinderBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinderBoardRepository  extends JpaRepository<FinderBoard, Long>, CustomFinderBoardRepository {
}
