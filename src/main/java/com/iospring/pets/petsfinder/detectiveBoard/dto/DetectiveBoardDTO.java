package com.iospring.pets.petsfinder.detectiveBoard.dto;


import com.iospring.pets.petsfinder.detectiveBoard.entity.DetectiveBoard;
import lombok.Data;

@Data
public class DetectiveBoardDTO {

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

    private String mainImageUrl;
    private String missingLocation;
    private Long id;
    private Integer money;
    private Double missingLatitude;
    private Double missingLongitude;
    private String missingTime;


}
