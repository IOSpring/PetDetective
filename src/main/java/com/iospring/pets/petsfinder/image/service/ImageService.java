package com.iospring.pets.petsfinder.image.service;

import com.iospring.pets.petsfinder.image.ImageRepository;
import com.iospring.pets.petsfinder.image.entity.Image;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    @Transactional
    public Image createImage(String breed, String color ,String fileUrl) {
        Image image = new Image();


        image.setBreed(breed);
        image.setColor(color);
        image.setUrl(fileUrl);

        imageRepository.save(image);

        return image;
    }
}
