package com.iospring.pets.petsfinder.detectBoard.repository;

import com.iospring.pets.petsfinder.detectBoard.dto.DetectBoardDTO;

import java.util.List;

public interface DetectBoardRepositoryCustom {
     List<DetectBoardDTO> findAllDetectBoardDTO(int page);
}
