package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;

import java.io.InputStream;

@Service
public class S3Service {
    private final AmazonS3 amazonS3;

    @Value("${s3.bucket-name}")
    private String bucketName;

    @Autowired
    public S3Service(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public PutObjectResult uploadFile(String key, MultipartFile file) {
        try {
        	 InputStream inputstream = file.getInputStream();
        	String contentType = file.getContentType();
            ObjectMetadata metadata = new ObjectMetadata();

            metadata.setContentLength(inputstream.available());
//            metadata.setContentType(contentType);
          return amazonS3.putObject(bucketName, key, inputstream, metadata);
       
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload file to S3", e);
        }
    }
}
