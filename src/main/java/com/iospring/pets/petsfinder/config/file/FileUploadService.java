package com.iospring.pets.petsfinder.config.file;

import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.iospring.pets.petsfinder.config.s3.S3Service;
import com.iospring.pets.petsfinder.exception.CustomException;
import com.iospring.pets.petsfinder.image.entity.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import static com.iospring.pets.petsfinder.exception.ErrorCode.FAIL_UPLOAD_IN_S3;


@RequiredArgsConstructor
@Service
public class FileUploadService {


    private final S3Service s3Service;

    public void s3DeleteImage(String fileName) {
        s3Service.deleteFile(fileName);
    }

    public String s3Upload(MultipartFile file,String host, String folderName) {

        String fileName;

        if (host.equals("localhost:8080"))
            fileName = "local/"+createFileName(file.getOriginalFilename());
        else
            fileName = folderName + "/" + createFileName(file.getOriginalFilename());

        System.out.println("fileName = " + fileName);
        
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());

        try(InputStream inputStream = file.getInputStream()){
            s3Service.uploadFile(inputStream, objectMetadata, fileName);
        } catch (IOException e) {
            throw new CustomException(FAIL_UPLOAD_IN_S3);
        }

        return s3Service.getFileUrl(fileName);
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
