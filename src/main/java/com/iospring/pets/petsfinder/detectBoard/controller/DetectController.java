package com.iospring.pets.petsfinder.detectBoard.controller;

import com.iospring.pets.petsfinder.detectBoard.dto.DetectBoardDTO;
import com.iospring.pets.petsfinder.detectBoard.dto.DetectBoardDetailDTO;
import com.iospring.pets.petsfinder.detectBoard.dto.DetectBoardForm;
import com.iospring.pets.petsfinder.detectBoard.entity.DetectiveBoard;
import com.iospring.pets.petsfinder.detectBoard.repository.DetectBoardRepository;
import com.iospring.pets.petsfinder.detectBoard.service.DetectBoardService;
import com.iospring.pets.petsfinder.image.entity.Image;
import com.iospring.pets.petsfinder.pet.entity.Pet;
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


    @PostMapping("/detect")
    public DetectBoardDTO addDetectiveBoard(DetectBoardForm detectBoardForm, MultipartFile file, @RequestHeader("Host") String host) {
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
        System.out.println("board_id = " + boardId);

        return detectBoardService.getDetailDetectBoard(boardId);
    }

    @DeleteMapping("/detect/{board_id}")
    public Long deleteDetectiveBoard(@PathVariable(name = "board_id") Long id) {
        detectBoardService.deleteBoard(id);
        return id;
    }


    @Data
    class DetectBoardDTOListAndToTalPage {
        List<DetectBoardDTO> detectBoardDTOList;
        long totalPage;
    }
}
