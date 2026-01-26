package com.kollu.angularone.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.kollu.angularone.model.UserModel;

public class UserModelUserDetails implements UserDetails {

	private String name;
	private String password;
	private List<GrantedAuthority> authorities;

	//by using constructor we are initializing values here 
	public UserModelUserDetails(UserModel userModel) {
		name = userModel.getName();
		password = userModel.getPassword();
		// User can have multiple roles while authorization in springConfig file, User
		// can be Developer and Lead role
		authorities = Arrays.stream(userModel.getRoles().split(",")).map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());

	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return name;
	}

}
