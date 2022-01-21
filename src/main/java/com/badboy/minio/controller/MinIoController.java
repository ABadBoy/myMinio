package com.badboy.minio.controller;

import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.GetObjectResponse;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author badboy
 */
@RestController
public class MinIoController {

  @Autowired
  private MinioClient minioClient;

  @PostMapping("/upload/minio")
  public ResponseEntity upload(@RequestParam(name = "multipartFile") MultipartFile file, String bucketName) throws Exception {
    BucketExistsArgs bucketExistsArgs = BucketExistsArgs.builder().bucket(bucketName).build();
    boolean isExist = minioClient.bucketExists(bucketExistsArgs);
    if (!isExist) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    String originalFilename = file.getOriginalFilename();
    minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(originalFilename)
        .stream(file.getInputStream(), file.getSize(), -1).contentType(file.getContentType()).build());
    return new ResponseEntity(HttpStatus.OK);
  }

  @GetMapping("/download")
  public ResponseEntity<ByteArrayResource> download(String fileName, String bucketName) throws Exception {
    GetObjectResponse objectResponse = minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(fileName).build());
    byte[] bytes = IOUtils.toByteArray(objectResponse);
    fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString());
    return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType("application/octet-stream"))
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename= \"" + fileName + "\"")
        .body(new ByteArrayResource(bytes));
  }

  @GetMapping("/download2")
  public ResponseEntity<InputStreamResource> download2(String fileName, String bucketName) throws Exception {
    GetObjectResponse objectResponse = minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(fileName).build());
    fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString());
    return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType("application/octet-stream"))
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename= \"" + fileName + "\"")
        .body(new InputStreamResource(objectResponse));
  }
}
