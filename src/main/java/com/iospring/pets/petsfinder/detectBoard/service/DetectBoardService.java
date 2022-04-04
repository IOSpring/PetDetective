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
import com.iospring.pets.petsfinder.image.service.ImageService;
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
    private final FileUploadService fileUploadService;
    private final ImageService imageService;

    @Transactional
    public DetectiveBoard addFindBoard(DetectBoardForm detectBoardForm, MultipartFile file) {


        String imageUrl = fileUploadService.s3Upload(file);
        Image image = imageService.createImage(detectBoardForm.getBreed(), detectBoardForm.getColor(),imageUrl);

        Pet pet = Pet.createPet(detectBoardForm);

        pet.setImage(image);
        petRepository.save(pet);

        DetectiveBoard detectiveBoard = DetectiveBoard.createDetectiveBoard(detectBoardForm);
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


        DetectBoardDetailDTO detectBoardDetailDTO = DetectBoardDetailDTO.createDetectBoardDetailDTO(detectiveBoard);
        return detectBoardDetailDTO;

    }



    @Transactional
    public Long deleteBoard(Long id) {

        DetectiveBoard detectiveBoard = detectBoardRepository.getById(id);
        Image image = detectiveBoard.getPet().getImage();

        String deletedFileName = image.getFileName();

        fileUploadService.s3DeleteImage(deletedFileName);

        detectBoardRepository.deleteById(id);
        return id;
    }
}
