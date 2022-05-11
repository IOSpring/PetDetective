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
    private Double distance;

    public FinderRequestDto(Object[] finderBoard) {
        this.boardId = (Long)finderBoard[0];
//        this.mainImageUrl =detectiveBoard.getPet().getImage().getUrl();
        this.mainImageUrl ="이미지 URL";
        this.findTime = (String)finderBoard[2];
        this.findLatitude = (Double)finderBoard[3];
        this.findLongitude = (Double)finderBoard[4];
        this.findLocation = (String)finderBoard[5];
        this.distance =(Double)finderBoard[6];
    }
}
