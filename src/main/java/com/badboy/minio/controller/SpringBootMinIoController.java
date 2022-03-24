package com.badboy.minio.controller;

import com.badboy.minio.service.MinioService;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author badboy
 */
@RestController
@RequestMapping("/springBoot")
public class SpringBootMinIoController {

  @Autowired
  private MinioService minioService;

  @PostMapping("/upload/minio")
  public ResponseEntity upload(@RequestPart(name = "multipartFile") MultipartFile file, String bucketName) throws Exception {
    Path path = Paths.get("aaa/"+file.getOriginalFilename());
    minioService.upload(bucketName,path,file.getInputStream());
    return new ResponseEntity(HttpStatus.OK);
  }

  @GetMapping("/download")
  public ResponseEntity<InputStreamResource> download(String fileName, String bucketName) throws Exception {
    Path path = Paths.get(fileName);
    fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString());
    InputStream download = minioService.download(bucketName, path);
    return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType("application/octet-stream"))
        .header(HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename= \"" + fileName + "\"")
        .body(new InputStreamResource(download));
  }

  @PostMapping("/delete/minio")
  public ResponseEntity deleteFile(String fileName, String bucketName) throws Exception {
    Path path = Paths.get(fileName);
    minioService.delete(bucketName, path);
    return new ResponseEntity(HttpStatus.OK);  }
}
