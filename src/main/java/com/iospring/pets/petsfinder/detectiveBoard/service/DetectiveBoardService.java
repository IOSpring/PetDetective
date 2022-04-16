package com.iospring.pets.petsfinder.detectiveBoard.service;

import com.iospring.pets.petsfinder.commond.apns.entity.CustomNotification;
import com.iospring.pets.petsfinder.commond.entity.BoardForm;
import com.iospring.pets.petsfinder.config.file.FileUploadService;
import com.iospring.pets.petsfinder.detectiveBoard.dto.DetectiveBoardDTO;
import com.iospring.pets.petsfinder.detectiveBoard.dto.DetectiveBoardDetailDTO;
import com.iospring.pets.petsfinder.detectiveBoard.entity.DetectiveBoard;
import com.iospring.pets.petsfinder.detectiveBoard.repository.DetectiveBoardRepository;
import com.iospring.pets.petsfinder.detectiveBoard.repository.DetectiveBoardRepositoryCustomImpl;
import com.iospring.pets.petsfinder.image.PetRepository;
import com.iospring.pets.petsfinder.image.entity.Image;
import com.iospring.pets.petsfinder.image.service.ImageService;
import com.iospring.pets.petsfinder.pet.entity.Pet;
import com.iospring.pets.petsfinder.pet.service.PetService;
import com.iospring.pets.petsfinder.user.entity.User;
import com.iospring.pets.petsfinder.user.repositoru.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DetectiveBoardService {

    private final UserRepository userRepository;
    private final DetectiveBoardRepository detectBoardRepository;
    private final FileUploadService fileUploadService;
    private final ImageService imageService;
    private final PetService petService;

    public void test() {
        List<DetectiveBoard> all = detectBoardRepository.findAll();

        CustomNotification customNotification = new CustomNotification();
        customNotification.setAlertBody("this is test123");
        customNotification.setAlertTitle("this is colaboration of 종서와 석준!!");
//        apnsConfig.pushCustomNotification(customNotification);

    }



    @Transactional
    public DetectiveBoardDTO addDetectiveBoard(BoardForm detectBoardForm, MultipartFile file, String host) {

        String imageUrl = fileUploadService.s3Upload(file,host, "detective");
        Image image = imageService.createImage(detectBoardForm ,imageUrl);
        Pet pet = petService.createPet(detectBoardForm);
        pet.connectImage(image);


        DetectiveBoard detectiveBoard = DetectiveBoard.toEntity(detectBoardForm);
        detectiveBoard.setPet(pet);
        // test user
            User user = userRepository.findByPhoneNumber("01099043322").orElseThrow(()->new RuntimeException("NO USER"));
            System.out.println("user.getEmail() = " + user.getEmail());
            detectiveBoard.setUser(user);
        //
        detectBoardRepository.save(detectiveBoard);


        DetectiveBoardDTO detectBoardDTO = DetectiveBoardDTO.createDetectBoardDTO(detectiveBoard, detectiveBoard.getPet().getImage().getUrl());

        return detectBoardDTO;
    }


    @Transactional
    public DetectiveBoard updateBoardImage(Long id, MultipartFile file, String host) {
        DetectiveBoard detectiveBoard = detectBoardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found board"));

        Image image = detectiveBoard.getPet().getImage();
        String fileName = image.getFileName();

        fileUploadService.s3DeleteImage(fileName);

        String newFileName = fileUploadService.s3Upload(file, host,"detective");

        image.setUrl(newFileName);

        return detectiveBoard;
    }

    @Transactional
    public DetectiveBoard updateBoardForm(Long id, BoardForm detectBoardForm) {

        DetectiveBoard detectiveBoard = detectBoardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found board"));

        detectiveBoard.updateImage(detectBoardForm);
        detectiveBoard.updateBoard(detectBoardForm);
        detectiveBoard.updatePet(detectBoardForm);

        return detectiveBoard;
    }


    public long getCount() {
        return (detectBoardRepository.count() / DetectiveBoardRepositoryCustomImpl.SHOW_DETECTIVE_BOARD_COUNT) +1;
    }


    public DetectiveBoardDetailDTO getDetailDetectBoard(Long boardId) {
        DetectiveBoard detectiveBoard = detectBoardRepository.getById(boardId);

        DetectiveBoardDetailDTO detectBoardDetailDTO = DetectiveBoardDetailDTO.createDetectBoardDetailDTO(detectiveBoard);
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
/*
        detectiveBoard.getPet().getImage().setBreed(detectBoardForm.getBreed());
        detectiveBoard.getPet().getImage().setColor(detectBoardForm.getColor());
        ->

        detectiveBoard.updateImage(detectBoardForm);
        */



        /*detectiveBoard.setMissingTime(detectBoardForm.getMissingTime());
        detectiveBoard.setMissLocation(detectBoardForm.getMissingLocation());
        detectiveBoard.setMissingLongitude(detectBoardForm.getMissingLongitude());
        detectiveBoard.setContent(detectBoardForm.getContent());
        detectiveBoard.setMoney(detectBoardForm.getMoney());
        detectiveBoard.setMissingLatitude(detectBoardForm.getMissingLatitude())
        ->

        detectiveBoard.updateBoard(detectBoardForm);
        */



        /*detectiveBoard.getPet().setGender(detectBoardForm.getGender());
        detectiveBoard.getPet().setDisease(detectBoardForm.getDisease());
        detectiveBoard.getPet().setOperation(detectBoardForm.isOperation());
        detectiveBoard.getPet().setFeature(detectBoardForm.getFeature());
        detectiveBoard.getPet().setAge(detectBoardForm.getAge());
        ->
        detectiveBoard.updatePet(detectBoardForm);
        */

