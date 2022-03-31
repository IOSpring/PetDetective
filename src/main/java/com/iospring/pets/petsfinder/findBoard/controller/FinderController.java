package com.iospring.pets.petsfinder.findBoard.controller;

import com.iospring.pets.petsfinder.config.file.FileUploadService;
import com.iospring.pets.petsfinder.findBoard.dto.FinderBoardForm;
import com.iospring.pets.petsfinder.findBoard.service.FinderBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController()
@RequiredArgsConstructor
public class FinderController {

    private final FinderBoardService finderBoardService;
    private final FileUploadService fileUploadService;

    @PostMapping("/find")
    public String addFinderBoarder(FinderBoardForm finderBoardForm, MultipartFile file, @RequestHeader("Host") String host) {
        System.out.println("file.getContentType() = " + file.getContentType());
        String fileUrl = fileUploadService.s3Upload(file);
        Long aLong = finderBoardService.addFindBoard(finderBoardForm,fileUrl);

        return "Success";


    }
}
