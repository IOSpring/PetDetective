package com.iospring.pets.petsfinder.commond.entity;

import lombok.Data;

@Data
public class BoardDTO {

    private String mainImageUrl;
    private String missingLocation;
    private Long id;
    private Double missingLatitude;
    private Double missingLongitude;
    private String missingTime;
}
