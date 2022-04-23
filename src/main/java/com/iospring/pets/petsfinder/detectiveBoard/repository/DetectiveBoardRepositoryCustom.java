package com.iospring.pets.petsfinder.detectiveBoard.repository;

import com.iospring.pets.petsfinder.detectiveBoard.dto.DetectiveBoardDTO;

import java.util.List;

public interface DetectiveBoardRepositoryCustom {
     List<DetectiveBoardDTO> findAllDetectBoardDto(int page);

     List<DetectiveBoardDTO> findDetectBoardDtoByLocation(int page, String condition);
     List<DetectiveBoardDTO> findDetectBoardDtoByBreed(int page, String condition);
     List<DetectiveBoardDTO> findDetectBoardDtoByColor(int page, String condition);

     long countDetectBoardDtoSearchedByLocation(String condition);
     long countDetectBoardDtoSearchedByBreed(String condition);
     long countDetectBoardDtoSearchedByColor(String condition);


}
