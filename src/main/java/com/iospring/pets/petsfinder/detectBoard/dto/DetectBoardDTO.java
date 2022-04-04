package com.iospring.pets.petsfinder.detectBoard.dto;


import com.iospring.pets.petsfinder.detectBoard.entity.DetectiveBoard;
import lombok.Data;

@Data
public class DetectBoardDTO {

    public static DetectBoardDTO createDetectBoardDTO(DetectiveBoard detectiveBoard, String url) {
        DetectBoardDTO detectBoardDTO = new DetectBoardDTO();

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
