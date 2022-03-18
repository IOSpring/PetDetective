package com.iospring.pets.petsfinder.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class TestController {

    @GetMapping("/")
    public String test() {
        return "Hello~~";
    }


    @PostMapping("/images")
    public String getImages(MultipartFile file) {
        if (file == null) {
            return "파일 안왔어용";
        }
        return file.getOriginalFilename();
    }
}


