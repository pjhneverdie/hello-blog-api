package com.jj.hello_blog.domain.common.aws.service;

import com.amazonaws.AmazonServiceException;
import lombok.RequiredArgsConstructor;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.net.URL;

@Service
@RequiredArgsConstructor
public class S3BucketService {
    private final AmazonS3 amazonS3;

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    private static final String BASIC_PATH = "image/";

    public String putS3Object(MultipartFile file) {
        try {
            String fileName = BASIC_PATH + file.getOriginalFilename();

            ObjectMetadata metadata = new ObjectMetadata();

            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());

            amazonS3.putObject(
                    new PutObjectRequest(bucketName, fileName, file.getInputStream(), metadata));

            return amazonS3.getUrl(bucketName, fileName).toString();

        } catch (IOException ie) {
            throw new RuntimeException(ie);
        }
    }

}
