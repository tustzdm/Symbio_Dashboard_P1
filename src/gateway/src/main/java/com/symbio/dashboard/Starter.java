package com.symbio.dashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.servlet.MultipartConfigElement;

@EnableScheduling
@SpringBootApplication
@Configuration
public class Starter {

  public static void main(String[] args) {
    SpringApplication.run(Starter.class, args);
  }

  @Bean
  public MultipartConfigElement multipartConfigElement() {
    MultipartConfigFactory factory = new MultipartConfigFactory();
    //  单个数据大小
    factory.setMaxFileSize("10240KB"); // KB,MB
    /// 总上传数据大小
    factory.setMaxRequestSize("102400KB");
    return factory.createMultipartConfig();
  }

}
