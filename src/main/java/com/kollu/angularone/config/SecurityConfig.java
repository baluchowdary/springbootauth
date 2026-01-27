package com.kollu.angularone.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.kollu.angularone.filter.JwtAuthFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Autowired
	private AppConfig appConfig;
	
	@Autowired
    private JwtAuthFilter authFilter;

	// authentication
	@Bean
	public UserDetailsService userDetailsService() {
		return new UserModelUserDetailsService();
	}

	// Scanning all requests and restricting access using filter
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				// 1. Disable CSRF (especially for H2 console and APIs)
				.csrf(csrf -> csrf.disable())
				// 2. Configure Authorization
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/h2-console/**", "/products/test", "/products/saveproduct", "/users/test",
								"/users/saveusermodel", "/users/authenticate").permitAll()
						.requestMatchers("/products/**", "/users/**").hasAnyRole("ADMIN", "LEAD")
						.anyRequest().authenticated())
				// 3. Configure Session Management (Stateless for JWT)
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				// 4. Configure Authentication Provider and Custom Filters
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
				// 5. Configure Headers for H2-Console
				.headers(headers -> headers.frameOptions(frame -> frame.disable()))
				// 6. Basic Authentication
				.httpBasic(Customizer.withDefaults())
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
	
	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
