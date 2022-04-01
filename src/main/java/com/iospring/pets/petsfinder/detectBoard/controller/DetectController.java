package com.iospring.pets.petsfinder.detectBoard.controller;

import com.iospring.pets.petsfinder.detectBoard.dto.DetectBoardDTO;
import com.iospring.pets.petsfinder.detectBoard.dto.DetectBoardDetailDTO;
import com.iospring.pets.petsfinder.detectBoard.dto.DetectBoardForm;
import com.iospring.pets.petsfinder.detectBoard.entity.DetectiveBoard;
import com.iospring.pets.petsfinder.detectBoard.repository.DetectBoardRepository;
import com.iospring.pets.petsfinder.detectBoard.service.DetectBoardService;
import com.iospring.pets.petsfinder.image.entity.Image;
import com.iospring.pets.petsfinder.pet.entity.Pet;
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
    public List<DetectBoardDTO> getDetectiveAllBoard(@RequestParam(required = false) int page) {
        System.out.println("page = " + page);
        return detectBoardRepository.findAllDetectBoardDTO(page);
    }

    @GetMapping("/detect/{board_id}")

    public DetectBoardDetailDTO getDetailDetectBoard(@PathVariable(name = "board_id") Long boardId) {
        System.out.println("board_id = " + boardId);

        return detectBoardService.getDetailDetectBoard(boardId);

    }
}
