package com.iospring.pets.petsfinder.finderBoard.controller;

import com.iospring.pets.petsfinder.commond.apns.entity.CustomNotification;
import com.iospring.pets.petsfinder.commond.apns.service.ApnsService;
import com.iospring.pets.petsfinder.exception.CustomException;
import com.iospring.pets.petsfinder.exception.ErrorCode;
import com.iospring.pets.petsfinder.finderBoard.dto.FinderBoardDTO;
import com.iospring.pets.petsfinder.finderBoard.dto.FinderBoardDetailDTO;
import com.iospring.pets.petsfinder.finderBoard.dto.FinderBoardForm;
import com.iospring.pets.petsfinder.finderBoard.entity.FinderBoard;
import com.iospring.pets.petsfinder.finderBoard.repository.FinderBoardRepository;
import com.iospring.pets.petsfinder.finderBoard.service.FinderBoardService;
import com.iospring.pets.petsfinder.goldentimemap.service.GoldenTimeService;
import com.iospring.pets.petsfinder.user.dto.DetectUserAlarmDto;
import com.iospring.pets.petsfinder.user.dto.FindUserAlarmDto;
import com.iospring.pets.petsfinder.user.dto.UserDTO;
import com.iospring.pets.petsfinder.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController()
@RequiredArgsConstructor
public class FinderController {

    private final FinderBoardService finderBoardService;
    private final FinderBoardRepository finderBoardRepository;
    private final ApnsService apnsService;
    private final UserService userService;
    private final GoldenTimeService goldenTimeService;


    @PostMapping("/finder")
    public Long addFinderBoard(FinderBoardForm finderBoardForm, MultipartFile file, @RequestHeader("host") String host, HttpSession httpSession) {
        String phoneNumber = (String) httpSession.getAttribute("phoneNumber");

        FinderBoardDTO finderBoardDTO = finderBoardService.addFindBoard(finderBoardForm, file,phoneNumber);

        List<FindUserAlarmDto> userDTOList = userService.findUsersIn3KmWhenUploadFinderBoard(finderBoardDTO, finderBoardForm.getBreed(), finderBoardForm.getColor(),finderBoardForm.getMissingTime());

        String threeHoursAgo = goldenTimeService.getThreeHoursAgo();

        CustomNotification customNotificationForGoldenTime = new CustomNotification();
        CustomNotification customNotificationForNormal = new CustomNotification();

        customNotificationForGoldenTime.setAlertTitle("긴급 알림 - 목격");
        customNotificationForNormal.setAlertTitle("새로운 게시글 - 목격");

        customNotificationForGoldenTime.setImageUrl(finderBoardDTO.getMainImageUrl());
        customNotificationForNormal.setImageUrl(finderBoardDTO.getMainImageUrl());

        if (finderBoardDTO.isCare()) {
            customNotificationForGoldenTime.createNotificationData("골든타임", "보호", finderBoardDTO.getId() + "");
            customNotificationForNormal.createNotificationData("게시글 작성", "보호", finderBoardDTO.getId() + "");
        }
        else {
            customNotificationForGoldenTime.createNotificationData("골든타임", "발견", finderBoardDTO.getId() + "");
            customNotificationForNormal.createNotificationData("게시글 작성", "발견", finderBoardDTO.getId() + "");
        }
        for (FindUserAlarmDto userDTO : userDTOList) {
            if (userDTO.getMissingTime().compareTo(threeHoursAgo) > 0) {
                apnsService.pushCustomNotification(customNotificationForGoldenTime, userDTO.getDeviceToken());
            }
            apnsService.pushCustomNotification(customNotificationForNormal, userDTO.getDeviceToken());
        }
        return finderBoardDTO.getId();
    }

    @GetMapping("/finder")
    public FindBoardDTOListAndToTalPage getFinderAllBoard(
                                  @RequestParam(name = "care", required = false) String care,
                                  @RequestParam(name = "page") int page) {
        if(care == null) {
            long totalPage = finderBoardService.getFindBoardPage();
            if (page > totalPage) throw new CustomException(ErrorCode.INVALID_PAGE);
            List<FinderBoardDTO> fullFinderBoardDTO = finderBoardRepository.getAllFinderBoardDTO(page);
            return new FindBoardDTOListAndToTalPage(fullFinderBoardDTO, totalPage);
        }
         else if (care.equals("true") ) {
            long totalPage = finderBoardService.getCareFindBoardPage();
            if (page > totalPage) throw new CustomException(ErrorCode.INVALID_PAGE);
            List<FinderBoardDTO> allCareFindBoardDTO = finderBoardRepository.getCareFinderBoardDTO(page);
            return new FindBoardDTOListAndToTalPage(allCareFindBoardDTO, totalPage);
        } else{
            long totalPage = finderBoardService.getNotCareFindBoardPage();
            if (page > totalPage) throw new CustomException(ErrorCode.INVALID_PAGE);
            List<FinderBoardDTO> notCareFinderBoardDTO = finderBoardRepository.getNotCareFinderBoardDTO(page);
            return new FindBoardDTOListAndToTalPage(notCareFinderBoardDTO, totalPage);
        }
    }

    @GetMapping("/finder/{board_id}")
    public FinderBoardDetailDTO getDetailDetectBoard(@PathVariable(name = "board_id") Long boardId) {
        return finderBoardService.getDetailDetectBoard(boardId);
    }

    @DeleteMapping("/finder/{board_id}")
    public Long deleteFinderBoard(@PathVariable(name = "board_id") Long id, HttpSession httpSession) {
        String phoneNumber = (String) httpSession.getAttribute("phoneNumber");
        Long deleteBoardId = finderBoardService.deleteBoard(id,phoneNumber);
        return  deleteBoardId;
    }

    @PutMapping("/finder/{board_id}")
    public FinderBoardDTO updateBoardForm(@PathVariable(name = "board_id") Long id,
                                             FinderBoardForm finderBoardForm,
                                             @RequestPart(required = false) MultipartFile file,
                                          HttpSession httpSession
    ) {
        String phoneNumber = (String) httpSession.getAttribute("phoneNumber");
        FinderBoard finderBoard = null;

        if (file != null) {
            finderBoard = finderBoardService.updateBoardImage(id, file,phoneNumber);
        }

        finderBoard = finderBoardService.updateBoardForm(id, finderBoardForm,phoneNumber);
        return FinderBoardDTO.createDetectBoardDTO(finderBoard, finderBoard.getPet().getImage().getUrl(),phoneNumber);
    }

    @GetMapping("/finder/search")
    public FindBoardDTOListAndToTalPage searchDetectiveBoard(
            @RequestParam(name = "category") String category,
            @RequestParam(name = "condition") String condition,
            @RequestParam(name = "page", defaultValue = "1") int page
    )
    {
        if (category.equals("loc")) {
            List<FinderBoardDTO> finderBoardDTOList = finderBoardRepository.findFinderBoardDtoByLocation(page, condition);
            long totalPageCount = finderBoardService.getPageCountSearchedByLocation(condition);

            if (page > totalPageCount) throw new CustomException(ErrorCode.INVALID_PAGE);

            return new FindBoardDTOListAndToTalPage(finderBoardDTOList,totalPageCount);

        } else if (category.equals("breed")) {
            List<FinderBoardDTO> finderBoardDTOList = finderBoardRepository.findFinderBoardDtoByBreed(page, condition);

            long totalPageCount = finderBoardService.getPageCountSearchedByBreed(condition);
            if (page > totalPageCount) throw new CustomException(ErrorCode.INVALID_PAGE);
            return new FindBoardDTOListAndToTalPage(finderBoardDTOList,totalPageCount);

        } else if (category.equals("color")) {
            List<FinderBoardDTO> finderBoardDTOList = finderBoardRepository.findFinderBoardDtoByColor(page, condition);
            long totalPageCount = finderBoardService.getPageCountSearchedByColor(condition);
            if (page > totalPageCount) throw new CustomException(ErrorCode.INVALID_PAGE);
            return new FindBoardDTOListAndToTalPage(finderBoardDTOList,totalPageCount);

        } else {
            throw new CustomException(ErrorCode.INVALID_CONDITION);
        }
    }

    @Data
    @AllArgsConstructor
    class CreateFinderBoardDTOAndUserMatchingBoardAndColor {
        FinderBoardDTO finderBoardDTO;
        List<UserDTO> matchingBoardAndColorUserList;
    }

    @Data
    @AllArgsConstructor
    class FindBoardDTOListAndToTalPage {
        List<FinderBoardDTO> finderBoardDTOS;
        long totalPage;
    }
}
