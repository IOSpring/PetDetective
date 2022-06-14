package com.iospring.pets.petsfinder.image.repository;

import com.iospring.pets.petsfinder.image.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
