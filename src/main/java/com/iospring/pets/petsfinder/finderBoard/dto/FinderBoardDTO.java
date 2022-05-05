package com.iospring.pets.petsfinder.finderBoard.dto;

import com.iospring.pets.petsfinder.commond.entity.BoardDTO;
import com.iospring.pets.petsfinder.finderBoard.entity.FinderBoard;
import lombok.Data;

@Data
public class FinderBoardDTO extends BoardDTO {
    private boolean isCare;

    public static FinderBoardDTO createDetectBoardDTO(FinderBoard finderBoardForm, String url, String userPhoneNumber) {
        FinderBoardDTO finderBoardDTO = new FinderBoardDTO();
        finderBoardDTO.setUserPhoneNumber(userPhoneNumber);
        finderBoardDTO.setId(finderBoardForm.getId());
        finderBoardDTO.setCare(finderBoardForm.isCare());
        finderBoardDTO.setMainImageUrl(url);
        finderBoardDTO.setMissingLocation(finderBoardForm.getMissLocation());
        finderBoardDTO.setMissingLongitude(finderBoardForm.getMissingLongitude());
        finderBoardDTO.setMissingLatitude(finderBoardForm.getMissingLatitude());
        finderBoardDTO.setMissingTime(finderBoardForm.getMissingTime());

        return finderBoardDTO;
    }
}
