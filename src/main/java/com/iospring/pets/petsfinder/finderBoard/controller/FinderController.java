package com.iospring.pets.petsfinder.finderBoard.controller;

import com.iospring.pets.petsfinder.config.file.FileUploadService;
import com.iospring.pets.petsfinder.finderBoard.dto.FinderBoardDTO;
import com.iospring.pets.petsfinder.finderBoard.dto.FinderBoardForm;
import com.iospring.pets.petsfinder.finderBoard.service.FinderBoardService;
import com.iospring.pets.petsfinder.user.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController()
@RequiredArgsConstructor
public class FinderController {

    private final FinderBoardService finderBoardService;

    @PostMapping("/finder")
    public CreateFinderBoardDTOAndUserMatchingBoardAndColor addFinderBoard(FinderBoardForm finderBoardForm,
                               MultipartFile file,
                               @RequestHeader("host") String host) {
        FinderBoardDTO finderBoardDTO = finderBoardService.addFindBoard(finderBoardForm, file, host);

        List<UserDTO> userDTOList = finderBoardService.userListMatchingBreedAndColor(finderBoardForm.getBreed(), finderBoardForm.getColor());
        /*
        TODO Alarm
         */

        return new CreateFinderBoardDTOAndUserMatchingBoardAndColor(finderBoardDTO, userDTOList);
    }

    @Data
    @AllArgsConstructor
    class CreateFinderBoardDTOAndUserMatchingBoardAndColor {
        FinderBoardDTO finderBoardDTO;
        List<UserDTO> matchingBoardAndColorUserList;

    }




}
