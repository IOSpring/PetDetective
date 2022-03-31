package com.iospring.pets.petsfinder.findBoard.repository;

import com.iospring.pets.petsfinder.findBoard.entity.FinderBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinderBoardRepository  extends JpaRepository<FinderBoard, Long> {
}
