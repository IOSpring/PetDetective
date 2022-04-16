package com.iospring.pets.petsfinder.finderBoard.repository;

import com.iospring.pets.petsfinder.finderBoard.entity.FinderBoard;
import com.iospring.pets.petsfinder.user.dto.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomFinderBoardRepository  {
    List<UserDTO> findUserListMatchingBreedAndColor(String breed , String color);
}
