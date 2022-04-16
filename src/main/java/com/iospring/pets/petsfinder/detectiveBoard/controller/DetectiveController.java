package com.iospring.pets.petsfinder.detectiveBoard.controller;

import com.iospring.pets.petsfinder.commond.apns.entity.CustomNotification;
import com.iospring.pets.petsfinder.commond.apns.service.ApnsService;
import com.iospring.pets.petsfinder.commond.entity.BoardForm;
import com.iospring.pets.petsfinder.detectiveBoard.dto.DetectiveBoardDTO;
import com.iospring.pets.petsfinder.detectiveBoard.dto.DetectiveBoardDetailDTO;
import com.iospring.pets.petsfinder.detectiveBoard.dto.DetectiveBoardForm;
import com.iospring.pets.petsfinder.detectiveBoard.entity.DetectiveBoard;
import com.iospring.pets.petsfinder.detectiveBoard.repository.DetectiveBoardRepository;
import com.iospring.pets.petsfinder.detectiveBoard.service.DetectiveBoardService;
import com.iospring.pets.petsfinder.user.dto.UserDTO;
import com.iospring.pets.petsfinder.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor()
public class DetectiveController {

    private final DetectiveBoardService detectBoardService;
    private final DetectiveBoardRepository detectBoardRepository;
    private final UserService userService;
    private final ApnsService apnsConfig;


   @PostMapping("/detect")
        public CreatedDetectiveBoardDTOAndFoundIn10KmUsers addDetectiveBoard(DetectiveBoardForm detectBoardForm, MultipartFile file, @RequestHeader("host") String host) {

            DetectiveBoardDTO detectBoardDTO = detectBoardService.addDetectiveBoard(detectBoardForm, file,host);

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

        List<DetectiveBoardDTO> allDetectBoardDTO = detectBoardRepository.findAllDetectBoardDTO(page);
        DetectBoardDTOListAndToTalPage detectBoardDTOListAndToTalPage = new DetectBoardDTOListAndToTalPage();
        detectBoardDTOListAndToTalPage.setDetectBoardDTOList(allDetectBoardDTO);
        detectBoardDTOListAndToTalPage.setTotalPage(count);

        return detectBoardDTOListAndToTalPage;
    }

    @GetMapping("/detect/{board_id}")
    public DetectiveBoardDetailDTO getDetailDetectBoard(@PathVariable(name = "board_id") Long boardId) {
        return detectBoardService.getDetailDetectBoard(boardId);
    }

    @DeleteMapping("/detect/{board_id}")
    public Long deleteDetectiveBoard(@PathVariable(name = "board_id") Long id) {
        detectBoardService.deleteBoard(id);
        return id;
    }


    @PutMapping("/detect/{board_id}")
    public DetectiveBoardDTO updateBoardForm(@PathVariable(name = "board_id") Long id,
                                             DetectiveBoardForm detectBoardForm,
                                             @RequestPart(required = false) MultipartFile file,
                                             @RequestHeader("host") String host
    ) {
        System.out.println("detectBoardForm = " + detectBoardForm);
        DetectiveBoard detectiveBoard = null;

        if (file != null) {
            detectiveBoard = detectBoardService.updateBoardImage(id, file,host);
        }

        detectiveBoard = detectBoardService.updateBoardForm(id, detectBoardForm);
        return DetectiveBoardDTO.createDetectBoardDTO(detectiveBoard, detectiveBoard.getPet().getImage().getUrl());
    }



    @Data
    class DetectBoardDTOListAndToTalPage {
        List<DetectiveBoardDTO> detectBoardDTOList;
        long totalPage;
    }

    @Data
    @AllArgsConstructor
    class CreatedDetectiveBoardDTOAndFoundIn10KmUsers {
        DetectiveBoardDTO createdDetectiveBoardDTO;
        List<UserDTO> foundIn10KM;
    }
}
