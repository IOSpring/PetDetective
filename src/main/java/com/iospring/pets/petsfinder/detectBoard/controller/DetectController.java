package com.iospring.pets.petsfinder.detectBoard.controller;

import com.iospring.pets.petsfinder.detectBoard.dto.DetectBoardDTO;
import com.iospring.pets.petsfinder.detectBoard.dto.DetectBoardDetailDTO;
import com.iospring.pets.petsfinder.detectBoard.dto.DetectBoardForm;
import com.iospring.pets.petsfinder.detectBoard.entity.DetectiveBoard;
import com.iospring.pets.petsfinder.detectBoard.repository.DetectBoardRepository;
import com.iospring.pets.petsfinder.detectBoard.service.DetectBoardService;
import com.iospring.pets.petsfinder.user.dto.UserDTO;
import com.iospring.pets.petsfinder.user.repositoru.UserRepository;
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
    private final UserRepository userRepository;

    @GetMapping("/test")
    public void aaa(){
        detectBoardService.test();

    }

    @PostMapping("/detect")
    public CreatedDetectiveBoardDTOAndFoundIn10KmUsers addDetectiveBoard(DetectBoardForm detectBoardForm, MultipartFile file) {

        DetectBoardDTO detectBoardDTO = detectBoardService.addFindBoard(detectBoardForm, file);
        List<Object[]> getDataInDB = userRepository.findUsersIn10KM(detectBoardDTO.getMissingLatitude(), detectBoardDTO.getMissingLongitude());

        List<UserDTO> foundIn10KM = userRepository.createUserDTOFromObject(getDataInDB);

        CreatedDetectiveBoardDTOAndFoundIn10KmUsers response = new CreatedDetectiveBoardDTOAndFoundIn10KmUsers();
        response.setCreatedDetectiveBoardDTO(detectBoardDTO);
        response.setFoundIn10KM(foundIn10KM);

        return response;
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
                                          @RequestPart(required = false) MultipartFile file) {

        System.out.println("detectBoardForm = " + detectBoardForm);
        
        DetectiveBoard detectiveBoard = null;

        if (file != null) {
            System.out.println("file.getOriginalFilename() = " + file.getOriginalFilename());
            detectiveBoard = detectBoardService.updateBoardImage(id, file);
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
    class CreatedDetectiveBoardDTOAndFoundIn10KmUsers {
        DetectBoardDTO createdDetectiveBoardDTO;
        List<UserDTO> foundIn10KM;
    }
}
