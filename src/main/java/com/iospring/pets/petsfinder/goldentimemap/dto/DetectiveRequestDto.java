package com.iospring.pets.petsfinder.goldentimemap.dto;

import com.iospring.pets.petsfinder.detectiveBoard.entity.DetectiveBoard;
import com.iospring.pets.petsfinder.finderBoard.entity.FinderBoard;
import lombok.Data;

@Data
public class DetectiveRequestDto {
    private Long boardId;
    private String mainImageUrl;
    private String missingTime;
    private Double missingLatitude;
    private Double missingLongitude;
    private String missingLocation;
    private Integer money;

    public DetectiveRequestDto(DetectiveBoard detectiveBoard) {
        this.boardId = detectiveBoard.getId();
        this.mainImageUrl =detectiveBoard.getPet().getImage().getUrl();
        this.missingTime = detectiveBoard.getMissingTime();
        this.missingLatitude = detectiveBoard.getMissingLatitude();
        this.missingLongitude = detectiveBoard.getMissingLongitude();
        this.missingLocation = detectiveBoard.getMissLocation();
        this.money = detectiveBoard.getMoney();
    }
}
