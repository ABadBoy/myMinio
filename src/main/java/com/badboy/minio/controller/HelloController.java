package com.badboy.minio.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * @author badboy
 */
@RestController
public class HelloController {

  @GetMapping("/hello")
  public String hello() {
    return "Hello World !";
  }

  @Autowired
  WebApplicationContext applicationContext;

  @GetMapping("/getParam")
  public String getParam(){

    RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
    // 拿到Handler适配器中的全部方法
    Map<RequestMappingInfo, HandlerMethod> methodMap = mapping.getHandlerMethods();
    List<String> urlList = new ArrayList<>();
    for (RequestMappingInfo info : methodMap.keySet()){

      Set<String> urlSet = info.getPatternsCondition().getPatterns();
      // 获取全部请求方式
      Set<RequestMethod> Methods = info.getMethodsCondition().getMethods();
      System.out.print(Methods.toString() +"\t");
      for (String url : urlSet){
        // 加上自己的域名和端口号，就可以直接调用
        urlList.add(url);
        System.out.println(url);
      }
    }
    return urlList.toString();
  }

}
