package com.demos.java.storage.minio.controller;

import com.demos.java.storage.minio.service.MinioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MinioControllerTest {

    @Mock
    private MinioService minioService;

    @InjectMocks
    private MinioController minioController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBucketSuccess() throws Exception {
        // 模拟成功创建桶
        doNothing().when(minioService).createBucket();
        
        // 调用方法
        ResponseEntity<String> response = minioController.createBucket();
        
        // 验证结果
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Bucket created successfully", response.getBody());
        verify(minioService, times(1)).createBucket();
    }

    @Test
    void testCreateBucketFailure() throws Exception {
        // 模拟创建桶失败
        doThrow(new Exception("Bucket creation failed")).when(minioService).createBucket();
        
        // 调用方法
        ResponseEntity<String> response = minioController.createBucket();
        
        // 验证结果
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("Error creating bucket"));
        verify(minioService, times(1)).createBucket();
    }

    @Test
    void testUploadFileSuccess() throws Exception {
        // 模拟成功上传文件
        doNothing().when(minioService).uploadFile(anyString(), any());
        
        // 创建模拟文件
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "test content".getBytes());
        
        // 调用方法
        ResponseEntity<String> response = minioController.uploadFile(file, "test.txt");
        
        // 验证结果
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("File uploaded successfully", response.getBody());
        verify(minioService, times(1)).uploadFile("test.txt", file);
    }

    @Test
    void testUploadFileFailure() throws Exception {
        // 模拟上传文件失败
        doThrow(new Exception("File upload failed")).when(minioService).uploadFile(anyString(), any());
        
        // 创建模拟文件
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "test content".getBytes());
        
        // 调用方法
        ResponseEntity<String> response = minioController.uploadFile(file, "test.txt");
        
        // 验证结果
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("Error uploading file"));
        verify(minioService, times(1)).uploadFile("test.txt", file);
    }

    @Test
    void testDownloadFileSuccess() throws Exception {
        // 模拟成功下载文件
        InputStream inputStream = new ByteArrayInputStream("test content".getBytes());
        when(minioService.downloadFile(anyString())).thenReturn(inputStream);
        
        // 调用方法
        ResponseEntity<byte[]> response = minioController.downloadFile("test.txt");
        
        // 验证结果
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(minioService, times(1)).downloadFile("test.txt");
    }

    @Test
    void testDownloadFileFailure() throws Exception {
        // 模拟下载文件失败
        when(minioService.downloadFile(anyString())).thenThrow(new Exception("File download failed"));
        
        // 调用方法
        ResponseEntity<byte[]> response = minioController.downloadFile("test.txt");
        
        // 验证结果
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(minioService, times(1)).downloadFile("test.txt");
    }

    @Test
    void testDeleteFileSuccess() throws Exception {
        // 模拟成功删除文件
        doNothing().when(minioService).deleteFile(anyString());
        
        // 调用方法
        ResponseEntity<String> response = minioController.deleteFile("test.txt");
        
        // 验证结果
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("File deleted successfully", response.getBody());
        verify(minioService, times(1)).deleteFile("test.txt");
    }

    @Test
    void testDeleteFileFailure() throws Exception {
        // 模拟删除文件失败
        doThrow(new Exception("File deletion failed")).when(minioService).deleteFile(anyString());
        
        // 调用方法
        ResponseEntity<String> response = minioController.deleteFile("test.txt");
        
        // 验证结果
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("Error deleting file"));
        verify(minioService, times(1)).deleteFile("test.txt");
    }

    @Test
    void testListFilesSuccess() throws Exception {
        // 模拟成功列出文件
        List<String> files = new ArrayList<>();
        files.add("file1.txt");
        files.add("file2.txt");
        when(minioService.listFiles()).thenReturn(files);
        
        // 调用方法
        ResponseEntity<List<String>> response = minioController.listFiles();
        
        // 验证结果
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(minioService, times(1)).listFiles();
    }

    @Test
    void testListFilesFailure() throws Exception {
        // 模拟列出文件失败
        when(minioService.listFiles()).thenThrow(new Exception("List files failed"));
        
        // 调用方法
        ResponseEntity<List<String>> response = minioController.listFiles();
        
        // 验证结果
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
        verify(minioService, times(1)).listFiles();
    }
}