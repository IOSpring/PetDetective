package com.iospring.pets.petsfinder.finderBoard.dto;


import com.iospring.pets.petsfinder.commond.entity.BoardForm;
import com.iospring.pets.petsfinder.finderBoard.entity.FinderBoard;
import lombok.Data;



@Data
public class FinderBoardForm extends BoardForm {
    private boolean isCare;

    @Override
    public String toString() {
        return  super.toString() + "FinderBoardForm{" +
                "isCare=" + isCare +
                '}';
    }

    public FinderBoard toEntity(FinderBoardForm finderBoardForm) {
        FinderBoard finderBoard = FinderBoard.builder()
                .isCare(finderBoardForm.isCare)
                .content(finderBoardForm.getContent())
                .missingTime(finderBoardForm.getMissingTime())
                .missLocation(finderBoardForm.getMissingLocation())
                .missingLatitude(finderBoardForm.getMissingLatitude())
                .missingLongitude(finderBoardForm.getMissingLongitude())
                .status("inprogress")
                .build();

        return finderBoard;

    }
}

