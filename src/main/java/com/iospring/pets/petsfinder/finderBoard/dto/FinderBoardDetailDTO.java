package com.iospring.pets.petsfinder.finderBoard.dto;

import com.iospring.pets.petsfinder.finderBoard.entity.FinderBoard;
import com.iospring.pets.petsfinder.image.entity.Image;
import com.iospring.pets.petsfinder.pet.entity.Pet;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FinderBoardDetailDTO {

    public static FinderBoardDetailDTO createDetectBoardDetailDTO(FinderBoard finderBoard) {

        Pet pet = finderBoard.getPet();
        Image image = finderBoard.getPet().getImage();

        FinderBoardDetailDTO detectBoardDetailDTO = FinderBoardDetailDTO.builder()
                .id(finderBoard.getId())
                .breed(image.getBreed())
                .color(image.getColor())
                .mainImageUrl(image.getUrl())
                .missingTime(finderBoard.getMissingTime())
                .missingLocation(finderBoard.getMissLocation())
                .content(finderBoard.getContent())
                .isOperation(pet.isOperation())
                .age(pet.getAge())
                .missingLatitude(finderBoard.getMissingLatitude())
                .missingLongitude(finderBoard.getMissingLongitude())
                .isCare(finderBoard.isCare())
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

    private boolean isCare;


}
