package com.iospring.pets.petsfinder.detectiveBoard.repository;

import com.iospring.pets.petsfinder.detectiveBoard.entity.DetectiveBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetectiveBoardRepository extends JpaRepository<DetectiveBoard, Long> , DetectiveBoardRepositoryCustom {


}
