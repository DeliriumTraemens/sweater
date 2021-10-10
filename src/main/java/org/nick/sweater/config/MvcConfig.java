package org.nick.sweater.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.swing.*;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
	@Value("${upload.path}")
	private String uploadPath;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		registry.addResourceHandler("/img/**")
				.addResourceLocations("classpath:/static/uploads/");
		
		//				.addResourceLocations("file:///" + uploadPath + "/");

		registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
//
	}
}
