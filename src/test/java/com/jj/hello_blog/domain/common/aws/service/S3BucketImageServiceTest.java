package com.jj.hello_blog.domain.common.aws.service;

import java.lang.reflect.Field;

import java.util.List;
import java.util.ArrayList;

import java.io.IOException;
import java.io.ByteArrayInputStream;

import java.net.URL;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import org.mockito.*;

import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.AmazonS3;

import org.springframework.mock.web.MockMultipartFile;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;

class S3BucketImageServiceTest {

    @Mock
    private AmazonS3 amazonS3;

    @InjectMocks
    private S3BucketImageService s3BucketImageService;

    @BeforeEach
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);

        Field bucketNameField = S3BucketImageService.class.getDeclaredField("bucketName");
        bucketNameField.setAccessible(true);
        bucketNameField.set(s3BucketImageService, "testBucket");
    }

    @Test
    public void putImage() throws IOException {
        // Given
        MockMultipartFile mockMultipartFile = new MockMultipartFile("testFile", "testFile.jpg", "image/jpg", new ByteArrayInputStream(("This is a test file").getBytes()));

        PutObjectResult putObjectResult = mock(PutObjectResult.class);

        String dummyURL = "https://hello-blog-api-dev-server-bucket.s3.ap-northeast-2.amazonaws.com/image/testFile.jpg";

        when(amazonS3.putObject(any(PutObjectRequest.class))).thenReturn(putObjectResult);
        when(amazonS3.getUrl(any(String.class), any(String.class))).thenReturn(new URL(dummyURL));

        // When
        String imageURL = s3BucketImageService.putImage(mockMultipartFile);

        // Then
        assertEquals(dummyURL, imageURL);
    }

    @Test
    public void moveFromTempToImage() throws IOException {
        // Given
        String tempURL = "https://hello-blog-api-dev-server-bucket.s3.ap-northeast-2.amazonaws.com/temp/testFile.jpg";
        String dummyURL = "https://hello-blog-api-dev-server-bucket.s3.ap-northeast-2.amazonaws.com/image/testFile.jpg";

        CopyObjectResult copyObjectResult = mock(CopyObjectResult.class);

        when(amazonS3.copyObject(any(String.class), any(String.class), any(String.class), any(String.class))).thenReturn(copyObjectResult);
        when(amazonS3.getUrl(any(String.class), any(String.class))).thenReturn(new URL(dummyURL));

        // When
        String url = s3BucketImageService.moveFromTempToImage(tempURL);

        // Then
        assertEquals(dummyURL, url);
    }

    @Test
    public void deleteImage() throws IOException {
        String dummyURL = "https://hello-blog-api-dev-server-bucket.s3.ap-northeast-2.amazonaws.com/image/testFile.jpg";

        doNothing().when(amazonS3).deleteObject(any(String.class), any(String.class));

        s3BucketImageService.deleteImage(dummyURL);
    }

    @Test
    public void deleteImages() throws IOException {
        List<String> dummyURLs = new ArrayList<>();
        dummyURLs.add("https://hello-blog-api-dev-server-bucket.s3.ap-northeast-2.amazonaws.com/image/testFile.jpg");

        DeleteObjectsResult deleteObjectsResult = mock(DeleteObjectsResult.class);

        when(amazonS3.deleteObjects(any(DeleteObjectsRequest.class))).thenReturn(deleteObjectsResult);

        s3BucketImageService.deleteImages(dummyURLs);
    }

}