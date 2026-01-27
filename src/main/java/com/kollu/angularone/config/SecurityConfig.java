package com.kollu.angularone.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Autowired
	private AppConfig appConfig;

	// authentication
	@Bean
	public UserDetailsService userDetailsService() {
		return new UserModelUserDetailsService();
	}

	// Scanning all requests and restricting access using filter
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(auth ->
		/* Below line, we are skipping authentication */
		auth.requestMatchers("/h2-console/**", "/products/test", "/products/saveproduct", "/users/test",
				"/users/saveusermodel").permitAll()
				/* Below line, we are doing ROLE based authentication */
				// .requestMatchers("/products/**", "/users/**").hasRole("ADMIN")
				.requestMatchers("/products/**", "/users/**").hasAnyRole("ADMIN", "LEAD").anyRequest().authenticated())
				.headers(headers -> headers.frameOptions(frame -> frame.disable())).httpBasic(Customizer.withDefaults())
				.build();
	}

	// Here, AuthenticationProvider communicating with UserDetails to validate user,
	// If we are not implementing this bean we will get Error in UI
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(appConfig.passwordEncoder());
		return authenticationProvider;
	}

}
