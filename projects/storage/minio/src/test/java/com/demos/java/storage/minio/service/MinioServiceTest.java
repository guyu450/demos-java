package com.demos.java.storage.minio.service;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.GetObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.ListObjectsArgs;
import io.minio.Result;
import io.minio.messages.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MinioServiceTest {

    @Mock
    private MinioClient minioClient;

    @InjectMocks
    private MinioService minioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // 通过反射设置bucketName
        try {
            java.lang.reflect.Field field = MinioService.class.getDeclaredField("bucketName");
            field.setAccessible(true);
            field.set(minioService, "demo-bucket");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testCreateBucketWhenBucketDoesNotExist() throws Exception {
        // 模拟bucket不存在
        when(minioClient.bucketExists(any(BucketExistsArgs.class))).thenReturn(false);
        
        // 调用方法
        minioService.createBucket();
        
        // 验证调用
        verify(minioClient, times(1)).bucketExists(any(BucketExistsArgs.class));
        verify(minioClient, times(1)).makeBucket(any(MakeBucketArgs.class));
    }

    @Test
    void testCreateBucketWhenBucketExists() throws Exception {
        // 模拟bucket存在
        when(minioClient.bucketExists(any(BucketExistsArgs.class))).thenReturn(true);
        
        // 调用方法
        minioService.createBucket();
        
        // 验证调用
        verify(minioClient, times(1)).bucketExists(any(BucketExistsArgs.class));
        verify(minioClient, never()).makeBucket(any(MakeBucketArgs.class));
    }

    @Test
    void testUploadFile() throws Exception {
        // 模拟bucket不存在
        when(minioClient.bucketExists(any(BucketExistsArgs.class))).thenReturn(false);
        
        // 创建模拟文件
        MultipartFile file = mock(MultipartFile.class);
        when(file.getInputStream()).thenReturn(new ByteArrayInputStream("test content".getBytes()));
        when(file.getSize()).thenReturn(12L);
        when(file.getContentType()).thenReturn("text/plain");
        
        // 调用方法
        minioService.uploadFile("test.txt", file);
        
        // 验证调用
        verify(minioClient, times(1)).bucketExists(any(BucketExistsArgs.class));
        verify(minioClient, times(1)).makeBucket(any(MakeBucketArgs.class));
        verify(minioClient, times(1)).putObject(any(PutObjectArgs.class));
    }

    @Test
    void testDownloadFile() throws Exception {
        // 模拟下载文件
        io.minio.GetObjectResponse getObjectResponse = mock(io.minio.GetObjectResponse.class);
        when(minioClient.getObject(any(GetObjectArgs.class))).thenReturn(getObjectResponse);
        
        // 调用方法
        java.io.InputStream result = minioService.downloadFile("test.txt");
        
        // 验证调用
        verify(minioClient, times(1)).getObject(any(GetObjectArgs.class));
        assertNotNull(result);
    }

    @Test
    void testDeleteFile() throws Exception {
        // 调用方法
        minioService.deleteFile("test.txt");
        
        // 验证调用
        verify(minioClient, times(1)).removeObject(any(RemoveObjectArgs.class));
    }

    @Test
    void testListFiles() throws Exception {
        // 模拟列出文件
        List<Result<Item>> results = new ArrayList<>();
        Item item1 = mock(Item.class);
        when(item1.objectName()).thenReturn("file1.txt");
        Result<Item> result1 = mock(Result.class);
        when(result1.get()).thenReturn(item1);
        results.add(result1);
        
        Item item2 = mock(Item.class);
        when(item2.objectName()).thenReturn("file2.txt");
        Result<Item> result2 = mock(Result.class);
        when(result2.get()).thenReturn(item2);
        results.add(result2);
        
        when(minioClient.listObjects(any(ListObjectsArgs.class))).thenReturn(results);
        
        // 调用方法
        List<String> files = minioService.listFiles();
        
        // 验证调用
        verify(minioClient, times(1)).listObjects(any(ListObjectsArgs.class));
        assertEquals(2, files.size());
        assertTrue(files.contains("file1.txt"));
        assertTrue(files.contains("file2.txt"));
    }
}