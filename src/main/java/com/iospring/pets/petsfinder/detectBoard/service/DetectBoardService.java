package com.iospring.pets.petsfinder.detectBoard.service;

import com.google.gson.Gson;
import com.iospring.pets.petsfinder.commond.apns.service.ApnsService;
import com.iospring.pets.petsfinder.commond.apns.entity.CustomNotification;
import com.iospring.pets.petsfinder.config.file.FileUploadService;
import com.iospring.pets.petsfinder.detectBoard.dto.DetectBoardDTO;
import com.iospring.pets.petsfinder.detectBoard.dto.DetectBoardDetailDTO;
import com.iospring.pets.petsfinder.detectBoard.dto.DetectBoardForm;
import com.iospring.pets.petsfinder.detectBoard.entity.DetectiveBoard;
import com.iospring.pets.petsfinder.detectBoard.repository.DetectBoardRepository;
import com.iospring.pets.petsfinder.detectBoard.repository.DetectBoardRepositoryCustomImpl;
import com.iospring.pets.petsfinder.image.PetRepository;
import com.iospring.pets.petsfinder.image.entity.Image;
import com.iospring.pets.petsfinder.image.service.ImageService;
import com.iospring.pets.petsfinder.pet.entity.Pet;
import com.turo.pushy.apns.ApnsClient;
import com.turo.pushy.apns.PushNotificationResponse;
import com.turo.pushy.apns.util.ApnsPayloadBuilder;
import com.turo.pushy.apns.util.SimpleApnsPushNotification;
import com.turo.pushy.apns.util.TokenUtil;
import com.turo.pushy.apns.util.concurrent.PushNotificationFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DetectBoardService {

    private final DetectBoardRepository detectBoardRepository;
    private final PetRepository petRepository;
    private final FileUploadService fileUploadService;
    private final ImageService imageService;
    private final ApnsService apnsConfig;

    public void test() {


        List<DetectiveBoard> all = detectBoardRepository.findAll();

        CustomNotification customNotification = new CustomNotification();
        customNotification.setAlertBody("this is test123");
        customNotification.setAlertTitle("this is colaboration of 종서와 석준!!");
        apnsConfig.pushCustomNotification(customNotification);

    }



    @Transactional
    public DetectBoardDTO
    addFindBoard(DetectBoardForm detectBoardForm, MultipartFile file) {

        String imageUrl = fileUploadService.s3Upload(file);

        Image image = imageService.createImage(detectBoardForm.getBreed(), detectBoardForm.getColor(),imageUrl);

        Pet pet = Pet.createPet(detectBoardForm);

        pet.setImage(image);

        petRepository.save(pet);

        DetectiveBoard detectiveBoard = DetectiveBoard.createDetectiveBoard(detectBoardForm);

        detectiveBoard.setPet(pet);


        detectBoardRepository.save(detectiveBoard);

        DetectBoardDTO detectBoardDTO = DetectBoardDTO.createDetectBoardDTO(detectiveBoard, detectiveBoard.getPet().getImage().getUrl());


        CustomNotification customNotification = new CustomNotification();
        customNotification.setAlertBody(detectBoardDTO.getMoney() +  "　의 현상금이 걸린 분실 게시글이 올라왔습니다");
        customNotification.setAlertTitle("신고 알림!");
        customNotification.setAlertId(detectBoardDTO.getId() + "");

        apnsConfig.pushCustomNotification(customNotification);


        // 경도 1 == 55.6키로
        // 경도 0.18 == 10키로

        // 위도 1 == 111키로
        // 위도 0.09== 10키로
        return detectBoardDTO;
    }


    @Transactional
    public DetectiveBoard updateBoardImage(Long id, MultipartFile file) {
        DetectiveBoard detectiveBoard = detectBoardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found board"));

        Image image = detectiveBoard.getPet().getImage();
        String fileName = image.getFileName();

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

        detectiveBoard.setMoney(detectBoardForm.getMoney());

        detectiveBoard.setMissingLatitude(detectBoardForm.getMissingLatitude());
        detectiveBoard.getPet().setFeature(detectBoardForm.getFeature());
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
