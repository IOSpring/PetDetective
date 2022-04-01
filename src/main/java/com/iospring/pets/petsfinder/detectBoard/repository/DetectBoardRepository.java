package com.iospring.pets.petsfinder.detectBoard.repository;

import com.iospring.pets.petsfinder.detectBoard.entity.DetectiveBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetectBoardRepository extends JpaRepository<DetectiveBoard, Long> , DetectBoardRepositoryCustom {


}
