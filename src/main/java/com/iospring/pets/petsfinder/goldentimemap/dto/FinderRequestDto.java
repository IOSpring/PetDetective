package com.iospring.pets.petsfinder.goldentimemap.dto;

import com.iospring.pets.petsfinder.finderBoard.entity.FinderBoard;
import lombok.Data;

@Data
public class FinderRequestDto {
    private Long boardId;
    private String mainImageUrl;
    private String findTime;
    private Double findLatitude;
    private Double findLongitude;
    private String findLocation;
    public FinderRequestDto(FinderBoard finderBoard) {
        this.boardId = finderBoard.getId();
        this.mainImageUrl = finderBoard.getPet().getImage().getUrl();
        this.findTime = finderBoard.getMissingTime();
        this.findLatitude = finderBoard.getMissingLatitude();
        this.findLongitude = finderBoard.getMissingLongitude();
        this.findLocation = finderBoard.getMissLocation();
    }
}
