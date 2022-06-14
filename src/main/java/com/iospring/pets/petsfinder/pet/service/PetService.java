package com.iospring.pets.petsfinder.pet.service;


import com.iospring.pets.petsfinder.commond.entity.BoardForm;
import com.iospring.pets.petsfinder.pet.repository.PetRepository;
import com.iospring.pets.petsfinder.pet.entity.Pet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PetService {
    private final PetRepository petRepository;
    @Transactional
    public Pet createPet(BoardForm boardForm) {
        Pet pet = Pet.builder()
                .gender(boardForm.getGender())
                .disease(boardForm.getDisease())
                .feature(boardForm.getFeature())
                .age(boardForm.getAge())
                .isOperation(boardForm.isOperation())
                .build();

        petRepository.save(pet);
        return pet;
    }
}
