package com.iospring.pets.petsfinder.goldentimemap.dto;

import lombok.Data;

import java.util.List;

@Data
public class GoldenTimeDto {
    private Double userLatitude;
    private Double userLongitude;
    private Double petLatitude;
    private Double petLongitude;

    private List<FinderRequestDto> findRequestDto;
    private List<DetectiveRequestDto> detectiveRequestDtos;

    public GoldenTimeDto(Double userLatitude,Double userLongitude,Double petLatitude,Double petLongitude) {
        this.userLatitude =userLatitude;
        this.userLongitude =userLongitude;
        this.petLatitude =petLatitude;
        this.petLongitude =petLongitude;
    }
}
