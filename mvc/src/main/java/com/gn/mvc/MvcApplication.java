package com.gn.mvc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class MvcApplication implements WebMvcConfigurer{
	
	@Value("${ffupload.location}")
	private String fileDir;

	public static void main(String[] args) {
		SpringApplication.run(MvcApplication.class, args);
	}
	
	// WebMvcConfigurer를 불러올수있는 코드
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/uploads/**")
			.addResourceLocations("file:///"+fileDir);
	}
	
}
