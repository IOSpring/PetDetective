package com.iospring.pets.petsfinder.finderBoard.controller;

import com.iospring.pets.petsfinder.commond.apns.entity.CustomNotification;
import com.iospring.pets.petsfinder.commond.apns.service.ApnsService;
import com.iospring.pets.petsfinder.detectiveBoard.controller.DetectiveController;
import com.iospring.pets.petsfinder.detectiveBoard.dto.DetectiveBoardDTO;
import com.iospring.pets.petsfinder.exception.CustomException;
import com.iospring.pets.petsfinder.exception.ErrorCode;
import com.iospring.pets.petsfinder.finderBoard.dto.FinderBoardDTO;
import com.iospring.pets.petsfinder.finderBoard.dto.FinderBoardDetailDTO;
import com.iospring.pets.petsfinder.finderBoard.dto.FinderBoardForm;
import com.iospring.pets.petsfinder.finderBoard.entity.FinderBoard;
import com.iospring.pets.petsfinder.finderBoard.repository.FinderBoardRepository;
import com.iospring.pets.petsfinder.finderBoard.service.FinderBoardService;
import com.iospring.pets.petsfinder.user.dto.UserDTO;
import com.iospring.pets.petsfinder.user.entity.User;
import com.iospring.pets.petsfinder.user.repositoru.UserRepository;
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



    @PostMapping("/finder")
    public CreateFinderBoardDTOAndUserMatchingBoardAndColor addFinderBoard(FinderBoardForm finderBoardForm, MultipartFile file, @RequestHeader("host") String host, HttpSession httpSession) {
        //세션에서 유저 정보 가져온다
        String phoneNumber = (String) httpSession.getAttribute("phoneNumber");
        // finder 게시글 저장한다.
        FinderBoardDTO finderBoardDTO = finderBoardService.addFindBoard(finderBoardForm, file, host,phoneNumber);
        // 유저가 finder 게시글을 올렸을 때 기존에 저장되어 있는 detective 게시판 중 품종과, 색이 같은 게시판을 올린 유저 리스트를 찾는다. (! 시간에 대한 로직은 없음 )
        List<UserDTO> userDTOList = userService.findUsersIn3KmWhenUploadFinderBoard(finderBoardDTO, finderBoardForm.getBreed(), finderBoardForm.getColor());


        for (UserDTO userDTO : userDTOList) {
            // 각 유저들에게 알람을 보낻다.
            CustomNotification customNotification = new CustomNotification();

            customNotification.setAlertTitle("목격 알림!");
            if (finderBoardDTO.isCare())
                customNotification.createNotificationData("새로운 게시글 작성", "보관", finderBoardDTO.getId() + "");
            else
                customNotification.createNotificationData("새로운 게시글 작성", "발견", finderBoardDTO.getId() + "");

            customNotification.setImageUrl(finderBoardDTO.getMainImageUrl());

            apnsService.pushCustomNotification(customNotification, userDTO.getDeviceToken());
        }

        return new CreateFinderBoardDTOAndUserMatchingBoardAndColor(finderBoardDTO, userDTOList);
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
                                             @RequestHeader("host") String host,
                                          HttpSession httpSession
    ) {
        String phoneNumber = (String) httpSession.getAttribute("phoneNumber");

        FinderBoard finderBoard = null;

        if (file != null) {
            finderBoard = finderBoardService.updateBoardImage(id, file,host,phoneNumber);
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
