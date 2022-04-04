package com.iospring.pets.petsfinder.detectBoard.dto;


import com.iospring.pets.petsfinder.detectBoard.entity.DetectiveBoard;import com.iospring.pets.petsfinder.image.entity.Image;
import com.iospring.pets.petsfinder.pet.entity.Pet;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DetectBoardDetailDTO {


    public static DetectBoardDetailDTO createDetectBoardDetailDTO(DetectiveBoard detectiveBoard) {

        Pet pet = detectiveBoard.getPet();
        Image image = detectiveBoard.getPet().getImage();
        
        DetectBoardDetailDTO detectBoardDetailDTO = DetectBoardDetailDTO.builder()
                .id(detectiveBoard.getId())
                .breed(image.getBreed())
                .color(image.getColor())
                .mainImageUrl(image.getUrl())
                .missingTime(detectiveBoard.getMissingTime())
                .missingLocation(detectiveBoard.getMissLocation())
                .money(detectiveBoard.getMoney())
                .content(detectiveBoard.getContent())
                .isOperation(pet.isOperation())
                .age(pet.getAge())
                .feature(pet.getFeature())
                .disease(pet.getDisease())
                .gender(pet.getGender())
                .build();

        return detectBoardDetailDTO;

    }

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
