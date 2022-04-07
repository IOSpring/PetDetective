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

import javax.transaction.TransactionScoped;

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

        // Upload file to S3
        String imageUrl = fileUploadService.s3Upload(file);

        // create Image entity
        Image image = imageService.createImage(detectBoardForm.getBreed(), detectBoardForm.getColor(),imageUrl);

        // create pet entity
        Pet pet = Pet.createPet(detectBoardForm);


        // connect image to pet
        pet.setImage(image);

        // save pet in database
        petRepository.save(pet);

        // create detective board entity
        DetectiveBoard detectiveBoard = DetectiveBoard.createDetectiveBoard(detectBoardForm);

        // connect pet to detective board
        detectiveBoard.setPet(pet);


        // save detective board in database
        detectBoardRepository.save(detectiveBoard);

        return detectiveBoard;
    }


    @Transactional
    public DetectiveBoard updateBoardImage(Long id, MultipartFile file) {
        DetectiveBoard detectiveBoard = detectBoardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found board"));

        Image image = detectiveBoard.getPet().getImage();
        String fileName = image.getFileName();

        System.out.println("deleted fileName = " + fileName);

        fileUploadService.s3DeleteImage(fileName);

        String newFileName = fileUploadService.s3Upload(file);

        image.setUrl(newFileName);

        return detectiveBoard;
    }

    @Transactional
    public DetectiveBoard updateBoardForm(Long id, DetectBoardForm detectBoardForm) {

        DetectiveBoard detectiveBoard = detectBoardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found board"));

        detectiveBoard.getPet().getImage().setBreed(detectBoardForm.getBreed());
        detectiveBoard.getPet().getImage().setColor(detectBoardForm.getColor());
        detectiveBoard.setMissingTime(detectBoardForm.getMissingTime());
        detectiveBoard.setMissLocation(detectBoardForm.getMissingLocation());
        detectiveBoard.setMissingLongitude(detectBoardForm.getMissingLongitude());

        detectiveBoard.setContent(detectBoardForm.getContent());

        detectiveBoard.setFeature(detectBoardForm.getFeature());

        detectiveBoard.setMoney(detectBoardForm.getMoney());

        detectiveBoard.setMissingLatitude(detectBoardForm.getMissingLatitude());

        detectiveBoard.getPet().setAge(detectBoardForm.getAge());

        detectiveBoard.getPet().setGender(detectBoardForm.getGender());
        detectiveBoard.getPet().setDisease(detectBoardForm.getDisease());
        detectiveBoard.getPet().setOperation(detectBoardForm.isOperation());

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
