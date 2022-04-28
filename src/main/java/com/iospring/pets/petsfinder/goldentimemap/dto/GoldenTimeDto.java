package com.iospring.pets.petsfinder.goldentimemap.dto;

import com.iospring.pets.petsfinder.detectiveBoard.entity.DetectiveBoard;
import com.iospring.pets.petsfinder.finderBoard.entity.FinderBoard;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class GoldenTimeDto {
    private List<FinderRequestDto> findRequestDto;
    private List<DetectiveRequestDto> missingRequestDto;
    public GoldenTimeDto(List<DetectiveBoard> detectiveBoards, List<FinderBoard> finderBoards) {
        findRequestDto = finderBoards.stream()
                .map(fdb -> new FinderRequestDto(fdb))
                .collect(Collectors.toList());
        missingRequestDto = detectiveBoards.stream()
                .map(dtb -> new DetectiveRequestDto(dtb))
                .collect(Collectors.toList());
    }
}
