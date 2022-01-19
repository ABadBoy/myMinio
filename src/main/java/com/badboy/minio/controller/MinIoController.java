package com.badboy.minio.controller;

import io.minio.BucketExistsArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

}
