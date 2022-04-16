package com.iospring.pets.petsfinder.finderBoard.controller;

import com.iospring.pets.petsfinder.finderBoard.dto.FinderBoardDTO;
import com.iospring.pets.petsfinder.finderBoard.dto.FinderBoardForm;
import com.iospring.pets.petsfinder.finderBoard.repository.FinderBoardRepository;
import com.iospring.pets.petsfinder.finderBoard.service.FinderBoardService;
import com.iospring.pets.petsfinder.user.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController()
@RequiredArgsConstructor
public class FinderController {

    private final FinderBoardService finderBoardService;
    private final FinderBoardRepository finderBoardRepository;
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

    @GetMapping("/finder")
    public FindBoardDTOListAndToTalPage getFinderAllBoard(
                                  @RequestParam(name = "care") boolean care,
                                  @RequestParam(name = "page") int page) {
        if (care) {
            long totalPage= finderBoardService.getCareFindBoardPage();
            if(page > totalPage ) return null;
            List<FinderBoardDTO> allCareFindBoardDTO = finderBoardRepository.getCareFinderBoardDTO(page);
            return new FindBoardDTOListAndToTalPage(allCareFindBoardDTO, totalPage);
        }
        else {
            long totalPage= finderBoardService.getNotCareFindBoardPage();
            if(page > totalPage ) return null;
            List<FinderBoardDTO> notCareFinderBoardDTO = finderBoardRepository.getNotCareFinderBoardDTO(page);
            return new FindBoardDTOListAndToTalPage(notCareFinderBoardDTO, totalPage);
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
