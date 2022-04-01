package com.iospring.pets.petsfinder.detectBoard.service;

import com.iospring.pets.petsfinder.config.file.FileUploadService;
import com.iospring.pets.petsfinder.config.s3.S3Service;
import com.iospring.pets.petsfinder.detectBoard.dto.DetectBoardForm;
import com.iospring.pets.petsfinder.detectBoard.entity.DetectiveBoard;
import com.iospring.pets.petsfinder.detectBoard.repository.DetectBoardRepository;
import com.iospring.pets.petsfinder.findBoard.dto.FinderBoardForm;
import com.iospring.pets.petsfinder.findBoard.entity.FinderBoard;
import com.iospring.pets.petsfinder.findBoard.repository.FinderBoardRepository;
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


}
