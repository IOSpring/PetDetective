package com.iospring.pets.petsfinder.goldentimemap.dto;

import com.iospring.pets.petsfinder.detectiveBoard.entity.DetectiveBoard;
import com.iospring.pets.petsfinder.finderBoard.entity.FinderBoard;
import lombok.Data;

import java.util.Date;

@Data
public class DetectiveRequestDto {
    private Long boardId;
    private String mainImageUrl;
    private String missingTime;
    private Double missingLatitude;
    private Double missingLongitude;
    private String missingLocation;
    private Integer money;

    private Double distance;

    public DetectiveRequestDto(Object[] detectiveBoard) {
        this.boardId = (Long)detectiveBoard[0];
        this.mainImageUrl =(String)detectiveBoard[1];
        this.missingTime = (String)detectiveBoard[2];
        this.missingLatitude = (Double)detectiveBoard[3];
        this.missingLongitude = (Double)detectiveBoard[4];
        this.missingLocation = (String)detectiveBoard[5];
        this.money = (Integer)detectiveBoard[6];
        this.distance =(Double)detectiveBoard[7];
    }
}
