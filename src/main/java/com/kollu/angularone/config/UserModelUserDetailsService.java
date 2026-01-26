package com.kollu.angularone.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.kollu.angularone.model.UserModel;
import com.kollu.angularone.repository.UserModelRepository;

public class UserModelUserDetailsService implements UserDetailsService {

	@Autowired
	private UserModelRepository userModelRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<UserModel> userModel = userModelRepository.findByName(username);
		return userModel.map(UserModelUserDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException("user not found " + username));
	}

}
