package com.iospring.pets.petsfinder.board.controller;

import com.iospring.pets.petsfinder.board.dto.FinderBoardForm;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class FinderController {

    @PostMapping("/find")
    public FinderBoardForm addFinderBoarder(@RequestBody FinderBoardForm finderBoardForm) {
        System.out.println("finderBoardForm = " + finderBoardForm);


        return finderBoardForm;



    }
}
