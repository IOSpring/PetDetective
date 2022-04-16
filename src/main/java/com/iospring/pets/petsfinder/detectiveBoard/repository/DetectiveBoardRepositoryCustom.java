package com.iospring.pets.petsfinder.detectiveBoard.repository;

import com.iospring.pets.petsfinder.detectiveBoard.dto.DetectiveBoardDTO;

import java.util.List;

public interface DetectiveBoardRepositoryCustom {
     List<DetectiveBoardDTO> findAllDetectBoardDTO(int page);

}
