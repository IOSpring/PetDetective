package com.iospring.pets.petsfinder.detectBoard.dto;


import com.iospring.pets.petsfinder.detectBoard.entity.DetectiveBoard;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DetectBoardDetailDTO {


    private String breed;
    private String color;
    private String missingTime;
    private String missingLocation;

    private boolean isOperation;
    private Integer age;
    private String feature;
    private String disease;
    private String gender;

    private String mainImageUrl;

    private Long id;
    private Integer money;
    private String content;

}
