package com.iospring.pets.petsfinder.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
public class TestController {

    @GetMapping("/")
    public String test() {
        return "Hello~~";
    }

    private final String FILE_PATH = "/Users/wonjongseo/aStudy/project/iospring/petsFinder/src/main/resources/static/img";

    @PostMapping("/images")
    public String getImage(MultipartFile file) {
        if (file == null) {
            return "파일 안왔어용";
        }

        return file.getOriginalFilename();
    }

    @PostMapping("/images2")
    public String getImages(MultipartFile file) {
        if (file == null) {
            return "파일 안왔어용";
        }
        String uploadFileName = file.getOriginalFilename();
        File saveFile = new File(FILE_PATH, uploadFileName);
        try{
            file.transferTo(saveFile);
            return saveFile.getAbsolutePath();
        } catch (IOException e) {
            System.out.println("e.getMessage() = " + e.getMessage());
            e.printStackTrace();
        }
        return file.getOriginalFilename();
    }
}


