package com.iospring.pets.petsfinder.image.service;

import com.iospring.pets.petsfinder.commond.entity.BoardForm;
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
    public Image createImage(BoardForm boardForm, String fileUrl) {
        Image image = Image.builder()
                .breed(boardForm.getBreed())
                .color(boardForm.getColor())
                .url(fileUrl)
                .build();

        imageRepository.save(image);

        return image;
    }
}
