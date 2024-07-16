package com.jj.hello_blog.common.aws.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import org.assertj.core.api.Assertions;

import org.mockito.*;

import org.springframework.mock.web.MockMultipartFile;

import java.net.URL;

import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayInputStream;

import java.lang.reflect.Field;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

class S3BucketServiceTest {

    @Mock
    private AmazonS3 amazonS3;

    @InjectMocks
    private S3BucketService s3BucketService;

    @BeforeEach
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);

        // 필드 주입
        Field bucketNameField = S3BucketService.class.getDeclaredField("bucketName");
        bucketNameField.setAccessible(true);
        bucketNameField.set(s3BucketService, "testBucket");
    }

    @Test
    public void testPutS3Object() throws IOException {
        // Given
        String originalFilename = "testFile.txt";
        String contentType = "text/plain";
        InputStream contentStream = new ByteArrayInputStream(("This is a test file").getBytes());
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", originalFilename, contentType, contentStream);
        URL url = new URL("http://localhost/" + originalFilename);

        when(amazonS3.putObject(any(PutObjectRequest.class))).thenReturn(null);
        when(amazonS3.getUrl(any(String.class), any(String.class))).thenReturn(url);

        // When
        String imageURL = s3BucketService.putS3Object(mockMultipartFile);

        // Then
        Assertions.assertThat(imageURL).isEqualTo(url.toString());
    }
}