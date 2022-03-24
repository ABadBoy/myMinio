package com.badboy.minio.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author badboy
 */
@Data
//@Component
//@ConfigurationProperties(prefix = "minio")
public class MinIoPropertiesConfig {

  private String endpoint;

  private String name;

  private String password;

}
