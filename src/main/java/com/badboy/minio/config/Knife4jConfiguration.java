package com.badboy.minio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author badboy
 */
@Configuration
@EnableOpenApi
public class Knife4jConfiguration {

  @Bean(value = "defaultApi")
  public Docket defaultApi() {
    Docket docket = new Docket(DocumentationType.OAS_30)
        .apiInfo(new ApiInfoBuilder()
            .description("# RESTful APIs")
            .termsOfServiceUrl("http://localhost:8080/")
            .contact(new Contact("badboy",null,null))
            .version("1.0")
            .build())
        //分组名称
        .groupName("1.0版本")
        .select()
        //这里指定Controller扫描包路径package com.hito.econ.gov.controller;
        .apis(RequestHandlerSelectors.basePackage("com.badboy.minio.controller"))
        .paths(PathSelectors.any())
        .build();
    return docket;
  }
}
