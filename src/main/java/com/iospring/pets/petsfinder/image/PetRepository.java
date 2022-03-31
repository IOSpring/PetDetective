package com.iospring.pets.petsfinder.image;

import com.iospring.pets.petsfinder.pet.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {
}
