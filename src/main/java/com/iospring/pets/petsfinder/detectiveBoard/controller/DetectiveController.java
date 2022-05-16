package com.iospring.pets.petsfinder.detectiveBoard.controller;

import com.iospring.pets.petsfinder.commond.apns.entity.CustomNotification;
import com.iospring.pets.petsfinder.commond.apns.service.ApnsService;
import com.iospring.pets.petsfinder.detectiveBoard.dto.DetectiveBoardDTO;
import com.iospring.pets.petsfinder.detectiveBoard.dto.DetectiveBoardDetailDTO;
import com.iospring.pets.petsfinder.detectiveBoard.dto.DetectiveBoardForm;
import com.iospring.pets.petsfinder.detectiveBoard.entity.DetectiveBoard;
import com.iospring.pets.petsfinder.detectiveBoard.repository.DetectiveBoardRepository;
import com.iospring.pets.petsfinder.detectiveBoard.service.DetectiveBoardService;
import com.iospring.pets.petsfinder.exception.CustomException;
import com.iospring.pets.petsfinder.user.dto.UserDTO;
import com.iospring.pets.petsfinder.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;

import static com.iospring.pets.petsfinder.exception.ErrorCode.INVALID_CONDITION;
import static com.iospring.pets.petsfinder.exception.ErrorCode.INVALID_PAGE;

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
        public CreatedDetectiveBoardDTOAndFoundIn10KmUsers addDetectiveBoard(DetectiveBoardForm detectBoardForm, MultipartFile file, @RequestHeader("host") String host,
                                  HttpSession httpSession) {
            //세션에서 유저 정보 가져온다
            String phoneNumber = (String) httpSession.getAttribute("phoneNumber");
            // detective 게시판 저장한다.
            DetectiveBoardDTO detectBoardDTO = detectBoardService.addDetectiveBoard(detectBoardForm, file,host,phoneNumber);


            CustomNotification customNotification = new CustomNotification();
            customNotification.setAlertBody("현상금 " + detectBoardDTO.getMoney() + "원!");
            customNotification.setAlertTitle("신고 알림!");
            customNotification.createNotificationData("새로운 게시글 작성", "의뢰", detectBoardDTO.getId() + "");
            customNotification.setImageUrl(detectBoardDTO.getMainImageUrl());

            apnsConfig.pushCustomNotification(customNotification);

            List<UserDTO> userWithIn10KM = userService.findUsersIn3KmWhenUploadDetectiveBoard(detectBoardDTO,detectBoardForm.getBreed(),detectBoardForm.getColor());

            return new CreatedDetectiveBoardDTOAndFoundIn10KmUsers(detectBoardDTO, userWithIn10KM);
    }



    @GetMapping("/detect")
    public DetectBoardDTOListAndToTalPage getDetectiveAllBoard(@RequestParam(required = false) int page) {
        long totalCount = detectBoardService.getPageCount();
        if (page > totalCount) {
            if (page > totalCount) throw new CustomException(INVALID_PAGE);
        }
        List<DetectiveBoardDTO> allDetectBoardDTO = detectBoardRepository.findAllDetectBoardDto(page);
        return  new DetectBoardDTOListAndToTalPage(allDetectBoardDTO,totalCount);
    }

    @GetMapping("/detect/{board_id}")
    public DetectiveBoardDetailDTO getDetailDetectBoard(@PathVariable(name = "board_id") Long boardId) {
        return detectBoardService.getDetailDetectBoard(boardId);
    }

    @DeleteMapping("/detect/{board_id}")
    public Long deleteDetectiveBoard(@PathVariable(name = "board_id") Long id , HttpSession httpSession) {
        String phoneNumber = (String) httpSession.getAttribute("phoneNumber");

        Long deleteBoardId = detectBoardService.deleteBoard(id,phoneNumber);
        return deleteBoardId;
    }

    @DeleteMapping("/a/detect/{board_id}")
    public Long aa(@PathVariable(name = "board_id") Long id ) {
        Long deleteBoardId = detectBoardService.deleteBoard(id);
        return deleteBoardId;
    }


    @PutMapping("/detect/{board_id}")
    public DetectiveBoardDTO updateBoardForm(@PathVariable(name = "board_id") Long id,
                                             DetectiveBoardForm detectBoardForm,
                                             @RequestPart(required = false) MultipartFile file,
                                             @RequestHeader("host") String host,
                                             HttpSession httpSession
    ) {

        String phoneNumber = (String) httpSession.getAttribute("phoneNumber");

        DetectiveBoard detectiveBoard = null;

        if (file != null) {
            detectiveBoard = detectBoardService.updateBoardImage(id, file,host,phoneNumber);
        }

        detectiveBoard = detectBoardService.updateBoardForm(id, detectBoardForm,phoneNumber);
        return DetectiveBoardDTO.createDetectBoardDTO(detectiveBoard, detectiveBoard.getPet().getImage().getUrl(), phoneNumber);
    }


    @GetMapping("/detect/search")
    public DetectBoardDTOListAndToTalPage searchDetectiveBoard(
            @RequestParam(name = "category") String category,
            @RequestParam(name = "condition") String condition,
            @RequestParam(name = "page", defaultValue = "1") int page
    ) {
        if (category.equals("loc")) {
            List<DetectiveBoardDTO> detectBoardDtoByLocation = detectBoardRepository.findDetectBoardDtoByLocation(page, condition);
            long totalCount = detectBoardService.getPageCountSearchedByLocation(condition);

            if (page > totalCount) throw new CustomException(INVALID_PAGE);

            return new DetectBoardDTOListAndToTalPage(detectBoardDtoByLocation,totalCount);

        } else if (category.equals("breed")) {
            List<DetectiveBoardDTO> detectBoardDtoByBreed = detectBoardRepository.findDetectBoardDtoByBreed(page, condition);

            long totalCount = detectBoardService.getPageCountSearchedByBreed(condition);

            if (page > totalCount) throw new CustomException(INVALID_PAGE);

            return new DetectBoardDTOListAndToTalPage(detectBoardDtoByBreed,totalCount);

        } else if (category.equals("color")) {
            List<DetectiveBoardDTO> detectBoardDtoByColor = detectBoardRepository.findDetectBoardDtoByColor(page, condition);
            long totalCount = detectBoardService.getPageCountSearchedByColor(condition);

            if (page > totalCount) throw new CustomException(INVALID_PAGE);

            return new DetectBoardDTOListAndToTalPage(detectBoardDtoByColor,totalCount);
        } else {
            throw new CustomException(INVALID_CONDITION);
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
