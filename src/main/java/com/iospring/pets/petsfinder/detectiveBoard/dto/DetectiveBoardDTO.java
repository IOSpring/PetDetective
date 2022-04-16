package com.iospring.pets.petsfinder.detectiveBoard.dto;


import com.iospring.pets.petsfinder.commond.entity.BoardDTO;
import com.iospring.pets.petsfinder.detectiveBoard.entity.DetectiveBoard;
import lombok.Data;

@Data
public class DetectiveBoardDTO extends BoardDTO {
    private Integer money;

    public static DetectiveBoardDTO createDetectBoardDTO(DetectiveBoard detectiveBoard, String url) {
        DetectiveBoardDTO detectBoardDTO = new DetectiveBoardDTO();

        detectBoardDTO.setId(detectiveBoard.getId());
        detectBoardDTO.setMoney(detectiveBoard.getMoney());
        detectBoardDTO.setMainImageUrl(url);
        detectBoardDTO.setMissingLocation(detectiveBoard.getMissLocation());
        detectBoardDTO.setMissingLongitude(detectiveBoard.getMissingLongitude());
        detectBoardDTO.setMissingLatitude(detectiveBoard.getMissingLatitude());
        detectBoardDTO.setMissingTime(detectiveBoard.getMissingTime());

        return detectBoardDTO;
    }




}
