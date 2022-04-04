package com.iospring.pets.petsfinder.detectBoard.service;

import com.iospring.pets.petsfinder.config.file.FileUploadService;
import com.iospring.pets.petsfinder.detectBoard.dto.DetectBoardDetailDTO;
import com.iospring.pets.petsfinder.detectBoard.dto.DetectBoardForm;
import com.iospring.pets.petsfinder.detectBoard.entity.DetectiveBoard;
import com.iospring.pets.petsfinder.detectBoard.repository.DetectBoardRepository;
import com.iospring.pets.petsfinder.detectBoard.repository.DetectBoardRepositoryCustomImpl;
import com.iospring.pets.petsfinder.image.ImageRepository;
import com.iospring.pets.petsfinder.image.PetRepository;
import com.iospring.pets.petsfinder.image.entity.Image;
import com.iospring.pets.petsfinder.pet.entity.Pet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DetectBoardService {
    private final DetectBoardRepository detectBoardRepository;
    private final PetRepository petRepository;
    private final ImageRepository imageRepository;
    private final FileUploadService fileUploadService;

    @Transactional
    public DetectiveBoard addFindBoard(DetectBoardForm detectBoardForm, MultipartFile file) {


        Pet pet = Pet.builder()
                .gender(detectBoardForm.getGender())
                .disease(detectBoardForm.getDisease())
                .feature(detectBoardForm.getFeature())
                .age(detectBoardForm.getAge())
                .isOperation(detectBoardForm.isOperation())
                .build();

        Image image = fileUploadService.s3UploadAndCreateImage(file, detectBoardForm.getBreed(), detectBoardForm.getColor());

        imageRepository.save(image);

        pet.setImage(image);
        petRepository.save(pet);

        DetectiveBoard detectiveBoard = new DetectiveBoard();
        detectiveBoard.setMoney(detectBoardForm.getMoney());
        detectiveBoard.setContent(detectBoardForm.getContent());
        detectiveBoard.setMissLocation(detectBoardForm.getMissingLocation());
        detectiveBoard.setMoney(detectBoardForm.getMoney());
        detectiveBoard.setMissingTime(detectBoardForm.getMissingTime());
        detectiveBoard.setMissingLatitude(detectBoardForm.getMissingLatitude());
        detectiveBoard.setMissingLongitude(detectBoardForm.getMissingLongitude());
        detectiveBoard.setPet(pet);

        detectBoardRepository.save(detectiveBoard);

        return detectiveBoard;
    }


    public long getCount() {
        return (detectBoardRepository.count() / DetectBoardRepositoryCustomImpl.SHOW_DETECTIVE_BOARD_COUNT) +1;
    }

    public DetectBoardDetailDTO getDetailDetectBoard(Long boardId) {
        DetectiveBoard detectiveBoard = detectBoardRepository.getById(boardId);
        System.out.println("detectiveBoard.getMoney() = " + detectiveBoard.getMoney());


        Pet pet = detectiveBoard.getPet();
        Image image = pet.getImage();

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

}
