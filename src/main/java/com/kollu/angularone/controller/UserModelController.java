package com.kollu.angularone.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kollu.angularone.model.AuthRequest;
import com.kollu.angularone.model.AuthResponse;
import com.kollu.angularone.model.UserModel;
import com.kollu.angularone.service.JwtService;
import com.kollu.angularone.service.UserModelService;

@RestController
@RequestMapping("/users")
public class UserModelController {
	
	
	@Autowired
	private UserModelService userModelService;
	
	 @Autowired
	 private AuthenticationManager authenticationManager;
	 
	 @Autowired
	 private JwtService jwtService;
	 
	 

	@GetMapping("/test")
	public String getUser() {
		return "Testing User controller";
	}

	@GetMapping("/getusermodel")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public List<UserModel> list() {
		return userModelService.getAllUsers();
	}

	@PostMapping("/saveusermodel")
	public UserModel register(@RequestBody UserModel user) {
		return userModelService.saveUser(user);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_LEAD')")
	public String delete(@PathVariable Long id) {
		userModelService.deleteUser(id);
		return "User deleted successfully";
	}
	
	/*
	 * @PostMapping("/authenticate") public String
	 * authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
	 * Authentication authentication = authenticationManager.authenticate(new
	 * UsernamePasswordAuthenticationToken(authRequest.getUsername(),
	 * authRequest.getPassword())); if (authentication.isAuthenticated()) { return
	 * jwtService.generateToken(authRequest.getUsername()); } else { throw new
	 * UsernameNotFoundException("invalid user request !"); } }
	 */
	
	@PostMapping("/authenticate")
//	public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
	public ResponseEntity<AuthResponse> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {

		String tokenResponce;

		try {
			// 1. Authenticate the user
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
			
			// 2. Check if authentication was successful
			if (authentication.isAuthenticated()) {
				/*
				 * FIX: Cast the principal to UserDetails. This allows JwtService to access the
				 * roles/authorities and include them in the token.
				 */
				 
				UserDetails userDetails = (UserDetails) authentication.getPrincipal();
				tokenResponce = jwtService.generateToken(userDetails);
			} else {
				throw new UsernameNotFoundException("Invalid user request!");
			}

			return ResponseEntity.ok(new AuthResponse(tokenResponce, true, "Login Successful"));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new AuthResponse(null, false, "Invalid Credentials"));
		}

	}
}
