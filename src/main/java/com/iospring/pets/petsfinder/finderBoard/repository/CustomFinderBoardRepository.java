package com.iospring.pets.petsfinder.finderBoard.repository;

import com.iospring.pets.petsfinder.finderBoard.dto.FinderBoardDTO;
import com.iospring.pets.petsfinder.finderBoard.entity.FinderBoard;
import com.iospring.pets.petsfinder.user.dto.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomFinderBoardRepository  {
    List<FinderBoardDTO> getCareFinderBoardDTO(int page);
    List<FinderBoardDTO> getNotCareFinderBoardDTO(int page);
    List<FinderBoardDTO> getAllFinderBoardDTO(int page);
    List<FinderBoardDTO> findFinderBoardDtoByLocation(int page, String condition);
    List<FinderBoardDTO> findFinderBoardDtoByBreed(int page, String condition);
    List<FinderBoardDTO> findFinderBoardDtoByColor(int page, String condition);

    long countFinderBoardDtoSearchedByLocation(String condition);
    long countDetectBoardDtoSearchedByBreed(String condition);
    long countFinderBoardDtoSearchedByColor(String condition);
}
//    List<UserDTO> findUserListMatchingBreedAndColor(String breed , String color);
