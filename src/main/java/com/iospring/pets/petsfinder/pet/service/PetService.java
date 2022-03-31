package com.iospring.pets.petsfinder.pet.service;


import com.iospring.pets.petsfinder.image.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;



}
