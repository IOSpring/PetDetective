package com.iospring.pets.petsfinder.config.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class S3Service {
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;


    public String deleteFile(String fileName) {
        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket, fileName);
        amazonS3.deleteObject(deleteObjectRequest);
        return fileName;
    }

    public void uploadFile(InputStream inputStream, ObjectMetadata objectMetadata, String fileName) {
        amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead));
    }


    public String getFileUrl(String fileName) {
        return amazonS3.getUrl(bucket, fileName).toString();
    }
}
