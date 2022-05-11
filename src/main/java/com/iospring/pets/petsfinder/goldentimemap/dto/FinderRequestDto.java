package com.iospring.pets.petsfinder.goldentimemap.dto;

import com.iospring.pets.petsfinder.finderBoard.entity.FinderBoard;
import lombok.Data;

import java.util.Date;

@Data
public class FinderRequestDto {
    private Long boardId;
    private String mainImageUrl;
    private String findTime;
    private Double findLatitude;
    private Double findLongitude;
    private String findLocation;
    private Double distance;

}
