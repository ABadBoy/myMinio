package com.badboy.minio.config;

import io.minio.MinioClient;
import javax.annotation.Resource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author badboy
 */
//@Configuration
//@EnableConfigurationProperties(MinIoPropertiesConfig.class)
public class MinioClientConfig {

  @Resource
  private MinIoPropertiesConfig minIoPropertiesConfig;


  @Bean
  public MinioClient minioClient() {
    MinioClient minioClient = MinioClient.builder()
        .endpoint(minIoPropertiesConfig.getEndpoint())
        .credentials(minIoPropertiesConfig.getName(), minIoPropertiesConfig.getPassword())
        .build();
    return minioClient;
  }


}
