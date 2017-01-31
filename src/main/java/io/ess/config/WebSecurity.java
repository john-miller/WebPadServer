package io.ess.config;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenEndpointFilter;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter {
	
	private Logger logger = Logger.getLogger(WebSecurity.class);
	
	@Autowired
	private DataSource dataSource;
		
	@Value("${username.query}")
	private String usernameQuery;
	
	@Value("${authorities.by.username}")
	private String authoritiesByUsername;
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
	    return super.authenticationManagerBean();
	} 	
		
	@Autowired
	public void configure(AuthenticationManagerBuilder auth)
			throws Exception {		
		logger.info("JDBC Username auth query: " + usernameQuery);
		logger.info("JDBC User roles query: " + authoritiesByUsername);			
		auth.jdbcAuthentication()
			.dataSource(dataSource)
			.passwordEncoder(new BCryptPasswordEncoder())
			.usersByUsernameQuery(usernameQuery)
			.authoritiesByUsernameQuery(authoritiesByUsername);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http	
			.authorizeRequests()
			.antMatchers("/oauth/token", "/oauth/check_token")
			.permitAll()
			.antMatchers(HttpMethod.OPTIONS).permitAll()
		.and()
			.authorizeRequests()
			.anyRequest()
			.hasRole("USER")
		.and()
			.httpBasic()
			.authenticationEntryPoint(new OAuth2AuthenticationEntryPoint())
		.and()
			.csrf()
				.disable()
				.addFilterBefore(new ClientCredentialsTokenEndpointFilter(), BasicAuthenticationFilter.class);
	}

}