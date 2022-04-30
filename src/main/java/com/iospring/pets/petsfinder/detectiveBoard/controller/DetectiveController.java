package com.iospring.pets.petsfinder.detectiveBoard.controller;

import com.iospring.pets.petsfinder.commond.apns.entity.CustomNotification;
import com.iospring.pets.petsfinder.commond.apns.service.ApnsService;
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

    @GetMapping("/a")
    public void testNotification() {
        CustomNotification customNotification = new CustomNotification();
        customNotification.setAlertBody("현상금 12341234"  + "원!");
        customNotification.setAlertTitle("신고 알림!");
        customNotification.createNotificationData("새로운  test 게시글 작성", "의뢰", "494");
        customNotification.setImageUrl("test image url");

        apnsConfig.pushCustomNotification(customNotification);


    }

   @PostMapping("/detect")
        public CreatedDetectiveBoardDTOAndFoundIn10KmUsers addDetectiveBoard(DetectiveBoardForm detectBoardForm, MultipartFile file, @RequestHeader("host") String host) {

            DetectiveBoardDTO detectBoardDTO = detectBoardService.addDetectiveBoard(detectBoardForm, file,host);

            CustomNotification customNotification = new CustomNotification();
            customNotification.setAlertBody("현상금 " + detectBoardDTO.getMoney() + "원!");
            customNotification.setAlertTitle("신고 알림!");
            customNotification.createNotificationData("새로운 게시글 작성", "의뢰", detectBoardDTO.getId() + "");
            customNotification.setImageUrl(detectBoardDTO.getMainImageUrl());

            apnsConfig.pushCustomNotification(customNotification);

            List<UserDTO> userWithIn10KM = userService.findUserWithIn10KM(detectBoardDTO);

            return new CreatedDetectiveBoardDTOAndFoundIn10KmUsers(detectBoardDTO, userWithIn10KM);
    }



    @GetMapping("/detect")
    public DetectBoardDTOListAndToTalPage getDetectiveAllBoard(@RequestParam(required = false) int page) {
        long totalCount = detectBoardService.getPageCount();
        if (page > totalCount) {
            if (page > totalCount) throw new RuntimeException("페이지를 초과했습니다.");
        }
        List<DetectiveBoardDTO> allDetectBoardDTO = detectBoardRepository.findAllDetectBoardDto(page);
        return  new DetectBoardDTOListAndToTalPage(allDetectBoardDTO,totalCount);
    }

    @GetMapping("/detect/{board_id}")
    public DetectiveBoardDetailDTO getDetailDetectBoard(@PathVariable(name = "board_id") Long boardId) {
        return detectBoardService.getDetailDetectBoard(boardId);
    }

    @DeleteMapping("/detect/{board_id}")
    public Long deleteDetectiveBoard(@PathVariable(name = "board_id") Long id) {
        Long deleteBoardId = detectBoardService.deleteBoard(id);
        return deleteBoardId;
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


    @GetMapping("/detect/search")
    public DetectBoardDTOListAndToTalPage searchDetectiveBoard(
            @RequestParam(name = "category") String category,
            @RequestParam(name = "condition") String condition,
            @RequestParam(name = "page", defaultValue = "1") int page
    ) {
        if (category.equals("loc")) {
            List<DetectiveBoardDTO> detectBoardDtoByLocation = detectBoardRepository.findDetectBoardDtoByLocation(page, condition);
            long totalPageCount = detectBoardService.getPageCountSearchedByLocation(condition);

            if (page > totalPageCount) throw new RuntimeException("페이지를 초과했습니다.");

            return new DetectBoardDTOListAndToTalPage(detectBoardDtoByLocation,totalPageCount);

        } else if (category.equals("breed")) {
            List<DetectiveBoardDTO> detectBoardDtoByBreed = detectBoardRepository.findDetectBoardDtoByBreed(page, condition);

            long totalPageCount = detectBoardService.getPageCountSearchedByBreed(condition);

            if (page > totalPageCount) throw new RuntimeException("페이지를 초과했습니다.");

            return new DetectBoardDTOListAndToTalPage(detectBoardDtoByBreed,totalPageCount);

        } else if (category.equals("color")) {
            List<DetectiveBoardDTO> detectBoardDtoByColor = detectBoardRepository.findDetectBoardDtoByColor(page, condition);
            long totalPageCount = detectBoardService.getPageCountSearchedByColor(condition);

            if (page > totalPageCount) throw new RuntimeException("페이지를 초과했습니다.");

            return new DetectBoardDTOListAndToTalPage(detectBoardDtoByColor,totalPageCount);
        } else {
            throw new RuntimeException("해당 조건으로 검색할 수 없습니다.");
        }

    }



    @Data
    @AllArgsConstructor
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
