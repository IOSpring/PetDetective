package com.iospring.pets.petsfinder.goldentimemap.dto;

import com.iospring.pets.petsfinder.detectiveBoard.entity.DetectiveBoard;
import com.iospring.pets.petsfinder.finderBoard.entity.FinderBoard;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class GoldenTimeDto {
    private Double userLatitude;
    private Double userLongitude;
    private Double petLatitude;
    private Double petLongitude;

    private List<FinderRequestDto> findRequestDto;
    private List<DetectiveRequestDto> detectiveRequestDtos;

    public GoldenTimeDto(List<Object[]> detectiveBoards, List<Object[]> finderBoards,Double userLatitude,Double userLongitude,Double petLatitude,Double petLongitude) {
        findRequestDto = finderBoards.stream()
                .map(fdb -> new FinderRequestDto(fdb))
                .collect(Collectors.toList());
        detectiveRequestDtos = detectiveBoards.stream()
                .map(dtb -> new DetectiveRequestDto(dtb))
                .collect(Collectors.toList());
        this.userLatitude =userLatitude;
        this.userLongitude =userLongitude;
        this.petLatitude =petLatitude;
        this.petLongitude =petLongitude;
    }
}
