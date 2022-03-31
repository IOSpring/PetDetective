package com.iospring.pets.petsfinder.findBoard.service;

import com.iospring.pets.petsfinder.findBoard.dto.FinderBoardForm;
import com.iospring.pets.petsfinder.findBoard.entity.FinderBoard;
import com.iospring.pets.petsfinder.findBoard.repository.FinderBoardRepository;
import com.iospring.pets.petsfinder.image.ImageRepository;
import com.iospring.pets.petsfinder.image.PetRepository;
import com.iospring.pets.petsfinder.image.entity.Image;
import com.iospring.pets.petsfinder.pet.entity.Pet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FinderBoardService {
    private final FinderBoardRepository finderBoardRepository;
    private final PetRepository petRepository;
    private final ImageRepository imageRepository;

    @Transactional
    public Long addFindBoard(FinderBoardForm finderBoardForm,String fileUrl) {

        Pet pet = Pet.builder()
                .gender(finderBoardForm.getGender())
                .disease(finderBoardForm.getDisease())
                .feature(finderBoardForm.getFeature())
                .age(finderBoardForm.getAge())
                .isOperation(finderBoardForm.getIsOperation().equals("T") ? true : false)
                .build();

        Image image = new Image();
        image.setBreed(finderBoardForm.getBreed());
        image.setColor(finderBoardForm.getColor());
        image.setUrl(fileUrl);
        imageRepository.save(image);

        pet.setImage(image);
        petRepository.save(pet);

        FinderBoard finderBoard = new FinderBoard();
        finderBoard.setCare(finderBoardForm.isCare());
        finderBoard.setLocation(finderBoardForm.getMissingLocation());
        finderBoard.setMoney(finderBoardForm.getMoney());
        finderBoard.setMissingTime(finderBoardForm.getMissingTime());
        finderBoard.setPet(pet);

        finderBoardRepository.save(finderBoard);

        return pet.getId();
    }


}
