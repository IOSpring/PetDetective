package com.iospring.pets.petsfinder.finderBoard.service;

import com.iospring.pets.petsfinder.config.file.FileUploadService;
import com.iospring.pets.petsfinder.finderBoard.dto.FinderBoardDTO;
import com.iospring.pets.petsfinder.finderBoard.dto.FinderBoardForm;
import com.iospring.pets.petsfinder.finderBoard.entity.FinderBoard;
import com.iospring.pets.petsfinder.finderBoard.repository.FinderBoardRepository;
import com.iospring.pets.petsfinder.image.entity.Image;
import com.iospring.pets.petsfinder.image.service.ImageService;
import com.iospring.pets.petsfinder.pet.entity.Pet;
import com.iospring.pets.petsfinder.pet.service.PetService;
import com.iospring.pets.petsfinder.user.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FinderBoardService {
    private final FinderBoardRepository finderBoardRepository;
    private final ImageService imageService;
    private final PetService petService;
    private final FileUploadService fileUploadService;

    @Transactional
    public FinderBoardDTO addFindBoard(FinderBoardForm finderBoardForm, MultipartFile file, String host) {

        String imageUrl = fileUploadService.s3Upload(file, host,"finder");

        Image image = imageService.createImage(finderBoardForm, imageUrl);
        Pet pet = petService.createPet(finderBoardForm);
        pet.connectImage(image);
        FinderBoard finderBoard = finderBoardForm.toEntity(finderBoardForm);
        finderBoard.setPet(pet);
        finderBoardRepository.save(finderBoard);


        return FinderBoardDTO.createDetectBoardDTO(finderBoard, imageUrl);
    }


    public List<UserDTO> userListMatchingBreedAndColor(String breed, String color) {
        return finderBoardRepository.findUserListMatchingBreedAndColor(breed,color);
    }
    /*
    발견 게시판 올리면 실종 게시판 찾은 다음에 그 유저 찾기 .
     */





}
