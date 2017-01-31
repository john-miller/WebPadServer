package io.ess.config;

import java.io.File;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class MVC extends WebMvcConfigurerAdapter {

	@Value("${external.images.dir}")
	private String imagesDir;
	
	@Value("${external.images.url}")
	private String imagesUrl;
	
	private Logger logger = Logger.getLogger(MVC.class);
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		/* Define external css directory here */
		String imagePath = "file:" + imagesDir + File.separator;
		
		logger.info("Using external images path: " + imagePath);
		
		registry
			.addResourceHandler(imagesUrl + "**")
			.addResourceLocations(imagePath);
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOrigins("*")
			.allowedMethods("GET", "POST", "OPTIONS")
			.allowedHeaders("Access-Control-Allow-Origin")
			.exposedHeaders("Access-Control-Allow-Origin")
			.allowCredentials(false).maxAge(3600);
		
		registry.addMapping("/oauth/token")
			.allowedOrigins("*")
			.allowedMethods("GET", "POST")
			.allowedHeaders("Access-Control-Allow-Origin")
			.exposedHeaders("Access-Control-Allow-Origin")
			.allowCredentials(false).maxAge(3600);
	}

}
