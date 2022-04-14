package com.iospring.pets.petsfinder.detectBoard.controller;

import com.iospring.pets.petsfinder.commond.apns.entity.CustomNotification;
import com.iospring.pets.petsfinder.commond.apns.service.ApnsService;
import com.iospring.pets.petsfinder.detectBoard.dto.DetectBoardDTO;
import com.iospring.pets.petsfinder.detectBoard.dto.DetectBoardDetailDTO;
import com.iospring.pets.petsfinder.detectBoard.dto.DetectBoardForm;
import com.iospring.pets.petsfinder.detectBoard.entity.DetectiveBoard;
import com.iospring.pets.petsfinder.detectBoard.repository.DetectBoardRepository;
import com.iospring.pets.petsfinder.detectBoard.service.DetectBoardService;
import com.iospring.pets.petsfinder.user.dto.UserDTO;
import com.iospring.pets.petsfinder.user.repositoru.UserRepository;
import com.iospring.pets.petsfinder.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor()
public class DetectController {

    private final DetectBoardService detectBoardService;
    private final DetectBoardRepository detectBoardRepository;
    private final UserService userService;
    private final ApnsService apnsConfig;

    @GetMapping("/test")
    public void aaa(){
        detectBoardService.test();
    }

    @PostMapping("/detect")
    public CreatedDetectiveBoardDTOAndFoundIn10KmUsers addDetectiveBoard(DetectBoardForm detectBoardForm, MultipartFile file, @RequestHeader("host") String host) {

        DetectBoardDTO detectBoardDTO = detectBoardService.addFindBoard(detectBoardForm, file,host);

        CustomNotification customNotification = new CustomNotification();
        customNotification.setAlertBody("현상금 " + detectBoardDTO.getMoney() + "원!");
        customNotification.setAlertTitle("신고 알림!");
        customNotification.setAlertId(detectBoardDTO.getId() + "");
        customNotification.setImageUrl(detectBoardDTO.getMainImageUrl());
        apnsConfig.pushCustomNotification(customNotification);

        List<UserDTO> userWithIn10KM = userService.findUserWithIn10KM(detectBoardDTO);

        return new CreatedDetectiveBoardDTOAndFoundIn10KmUsers(detectBoardDTO, userWithIn10KM);
    }



    @GetMapping("/detect")
    public DetectBoardDTOListAndToTalPage getDetectiveAllBoard(@RequestParam(required = false) int page) {
        long count = detectBoardService.getCount();

        if (page > count) {
            return null;
        }

        List<DetectBoardDTO> allDetectBoardDTO = detectBoardRepository.findAllDetectBoardDTO(page);
        DetectBoardDTOListAndToTalPage detectBoardDTOListAndToTalPage = new DetectBoardDTOListAndToTalPage();
        detectBoardDTOListAndToTalPage.setDetectBoardDTOList(allDetectBoardDTO);
        detectBoardDTOListAndToTalPage.setTotalPage(count);

        return detectBoardDTOListAndToTalPage;
    }

    @GetMapping("/detect/{board_id}")
    public DetectBoardDetailDTO getDetailDetectBoard(@PathVariable(name = "board_id") Long boardId) {
        return detectBoardService.getDetailDetectBoard(boardId);
    }

    @DeleteMapping("/detect/{board_id}")
    public Long deleteDetectiveBoard(@PathVariable(name = "board_id") Long id) {
        detectBoardService.deleteBoard(id);
        return id;
    }


    @PutMapping("/detect/{board_id}")
    public DetectBoardDTO updateBoardForm(@PathVariable(name = "board_id") Long id,
                                          DetectBoardForm detectBoardForm,
                                          @RequestPart(required = false) MultipartFile file,
                                          @RequestHeader("host") String host
    ) {

        System.out.println("detectBoardForm = " + detectBoardForm);
        
        DetectiveBoard detectiveBoard = null;

        if (file != null) {
            System.out.println("file.getOriginalFilename() = " + file.getOriginalFilename());
            detectiveBoard = detectBoardService.updateBoardImage(id, file,host);
        }

        detectiveBoard = detectBoardService.updateBoardForm(id, detectBoardForm);

        return DetectBoardDTO.createDetectBoardDTO(detectiveBoard, detectiveBoard.getPet().getImage().getUrl());
    }



    @Data
    class DetectBoardDTOListAndToTalPage {
        List<DetectBoardDTO> detectBoardDTOList;
        long totalPage;
    }

    @Data
    @AllArgsConstructor
    class CreatedDetectiveBoardDTOAndFoundIn10KmUsers {
        DetectBoardDTO createdDetectiveBoardDTO;
        List<UserDTO> foundIn10KM;
    }
}
