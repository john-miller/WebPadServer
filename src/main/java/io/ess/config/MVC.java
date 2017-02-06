package io.ess.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class MVC extends WebMvcConfigurerAdapter {

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
