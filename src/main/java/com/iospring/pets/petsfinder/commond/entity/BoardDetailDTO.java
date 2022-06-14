package com.iospring.pets.petsfinder.commond.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BoardDetailDTO {
    private String breed;
    private String color;
    private String missingTime;
    private String missingLocation;
    private Double missingLatitude;
    private Double missingLongitude;
    private boolean isOperation;
    private Integer age;
    private String feature;
    private String disease;
    private String gender;
    private String mainImageUrl;
    private Long id;
    private String content;
}
