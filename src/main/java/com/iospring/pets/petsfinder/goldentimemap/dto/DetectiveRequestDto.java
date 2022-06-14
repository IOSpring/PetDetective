package com.iospring.pets.petsfinder.goldentimemap.dto;

import com.iospring.pets.petsfinder.detectiveBoard.dto.DetectiveBoardDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

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
}
