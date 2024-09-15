package com.jj.hello_blog.domain.common.aws.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import java.net.URL;
import java.net.URLDecoder;
import java.net.MalformedURLException;

import java.util.List;
import java.util.ArrayList;

import java.time.LocalDateTime;

import lombok.RequiredArgsConstructor;

import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.AmazonS3;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;

@Service
@RequiredArgsConstructor
public class S3BucketImageService {

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    private final AmazonS3 amazonS3;

    private static final String BASE_PATH = "image/";

    /**
     * putImage, 이미지 단건 업로드
     */
    public String putImage(MultipartFile imageFile) {
        try {
            String key = BASE_PATH + imageFile.getOriginalFilename() + LocalDateTime.now();

            ObjectMetadata metadata = new ObjectMetadata();

            metadata.setContentLength(imageFile.getSize());
            metadata.setContentType(imageFile.getContentType());

            amazonS3.putObject(new PutObjectRequest(bucketName, key, imageFile.getInputStream(), metadata));

            return amazonS3.getUrl(bucketName, key).toString();
        } catch (IOException ie) {
            throw new RuntimeException(ie);
        }
    }

    /**
     * putImage, 이미지 단건 업로드 (키 지정)
     */
    public String putImage(MultipartFile imageFile, String key) {
        try {
            ObjectMetadata metadata = new ObjectMetadata();

            metadata.setContentLength(imageFile.getSize());
            metadata.setContentType(imageFile.getContentType());

            amazonS3.putObject(new PutObjectRequest(bucketName, key, imageFile.getInputStream(), metadata));

            return amazonS3.getUrl(bucketName, key).toString();
        } catch (IOException ie) {
            throw new RuntimeException(ie);
        }
    }

    /**
     * moveFromTempToImage, 경로 변경
     */
    public String moveFromTempToImage(String tempUrl) {
        String key = extractKey(tempUrl);

        String movedKey = BASE_PATH + key.split("/")[1];

        amazonS3.copyObject(bucketName, key, bucketName, movedKey);

        return amazonS3.getUrl(bucketName, movedKey).toString();
    }

    /**
     * deleteS3Object, 단건 이미지 삭제
     */
    public void deleteImage(String url) {
        amazonS3.deleteObject(bucketName, extractKey(url));
    }

    /**
     * deleteS3Objects, 여러 건 이미지 삭제
     */
    public void deleteImages(List<String> urls) {
        List<String> keys = new ArrayList<>(urls.size());

        for (String url : urls) {
            keys.add(extractKey(url));
        }

        amazonS3.deleteObjects(new DeleteObjectsRequest(bucketName).withKeys(keys.toArray(new String[0])));
    }

    /**
     * extractKey, url에서 key를 추출
     */
    private String extractKey(String url) {
        try {
            String path = new URL(url).getPath();

            path = path.substring(1);

            return URLDecoder.decode(path, StandardCharsets.UTF_8);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

}
