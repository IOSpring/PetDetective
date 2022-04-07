package com.iospring.pets.petsfinder.detectBoard.controller;

import com.iospring.pets.petsfinder.detectBoard.dto.DetectBoardDTO;
import com.iospring.pets.petsfinder.detectBoard.dto.DetectBoardDetailDTO;
import com.iospring.pets.petsfinder.detectBoard.dto.DetectBoardForm;
import com.iospring.pets.petsfinder.detectBoard.entity.DetectiveBoard;
import com.iospring.pets.petsfinder.detectBoard.repository.DetectBoardRepository;
import com.iospring.pets.petsfinder.detectBoard.service.DetectBoardService;
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

    @PostMapping("/detect")
    public DetectBoardDTO addDetectiveBoard(DetectBoardForm detectBoardForm, MultipartFile file) {
        DetectiveBoard detectiveBoard = detectBoardService.addFindBoard(detectBoardForm, file);
        return DetectBoardDTO.createDetectBoardDTO(detectiveBoard, detectiveBoard.getPet().getImage().getUrl());
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
        DetectiveBoard detectiveBoard = null;

        if (file != null) {
            detectiveBoard = detectBoardService.updateBoardImage(id, file);
        }

        detectiveBoard = detectBoardService.updateBoardForm(id, detectBoardForm);

        System.out.println("detectBoardForm = " + detectBoardForm);
        return DetectBoardDTO.createDetectBoardDTO(detectiveBoard, detectiveBoard.getPet().getImage().getUrl());
    }



    @Data
    class DetectBoardDTOListAndToTalPage {
        List<DetectBoardDTO> detectBoardDTOList;
        long totalPage;
    }
}
