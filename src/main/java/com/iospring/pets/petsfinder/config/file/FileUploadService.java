package com.iospring.pets.petsfinder.config.file;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.iospring.pets.petsfinder.config.s3.S3Service;
import com.iospring.pets.petsfinder.image.entity.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;


@RequiredArgsConstructor
@Service
public class FileUploadService {


    private final S3Service s3Service;




    public Image s3UploadAndCreateImage(MultipartFile file, String  breed, String color) {
        Image image = new Image();

        String fileName = createFileName(file.getOriginalFilename());
        image.setFileName(fileName);
        image.setBreed(breed);
        image.setColor(color);
        image.setUrl(s3Service.getFileUrl(fileName));

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());

        try(InputStream inputStream = file.getInputStream()){
            s3Service.uploadFile(inputStream, objectMetadata, fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    private String createFileName(String originalFilename) {
        return UUID.randomUUID().toString().concat(getFileExtension(originalFilename));
    }

    private String getFileExtension(String originalFilename) {
        try {
            return originalFilename.substring(originalFilename.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new IllegalArgumentException(String.format("잘못된 파일의 형식 %s 입니다", originalFilename));
        }
    }
}
