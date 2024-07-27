package com.jj.hello_blog.domain.common.aws.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;

import com.jj.hello_blog.domain.common.aws.service.S3BucketService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.mock.web.MockMultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;

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
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "file",
                "testFile.jpg", "image/jpeg",
                new ByteArrayInputStream(("This is a test file").getBytes()));

        String dummyURL = "http://test/testFile.jpg";

        when(amazonS3.putObject(any(PutObjectRequest.class))).thenReturn(null);
        when(amazonS3.getUrl(any(String.class), any(String.class))).thenReturn(new URL(dummyURL));

        // When
        String imageURL = s3BucketService.putS3Object(mockMultipartFile);

        // Then
        Assertions.assertThat(imageURL).isEqualTo(dummyURL);
    }
}