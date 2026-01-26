package com.kollu.angularone.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configurable
@EnableWebSecurity
public class SecurityConfig {
	
	
		//authentication 
	 	@Bean
	    public UserDetailsService userDetailsService() {
	        return new UserModelUserDetailsService();
	    }
	 	
//	 	@Bean
//	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//	        return http.csrf().disable()
//	                .authorizeHttpRequests()
//	                .requestMatchers("/products/welcome","/products/new").permitAll()
//	                .and()
//	                .authorizeHttpRequests().requestMatchers("/products/**")
//	                .authenticated().and().formLogin().and().build();
//	    }
	 	
	 	@Bean
	 	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	 	    return http.csrf(AbstractHttpConfigurer::disable)
	 	            .authorizeHttpRequests(auth ->
					/* Below line, we are skipping authentication */ 
	 	                    auth.requestMatchers("/products/test", "/products/saveproduct").permitAll()
	 	                   /* Below line, we are doing authentication */
	 	                            .requestMatchers("/products/getproduct").authenticated()
	 	            )
	 	            .httpBasic(Customizer.withDefaults()).build();
	 	}
	 	
	 	//Here, We are encrypting password 
	 	@Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }

	 	//Here, AuthenticationProvider communicating with UserDetails to validate username, 
	 	//If we are not implementing this bean we will get Error in UI
	 	@Bean
	    public AuthenticationProvider authenticationProvider(){
	        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
	        authenticationProvider.setUserDetailsService(userDetailsService());
	        authenticationProvider.setPasswordEncoder(passwordEncoder());
	        return authenticationProvider;
	    }

}
