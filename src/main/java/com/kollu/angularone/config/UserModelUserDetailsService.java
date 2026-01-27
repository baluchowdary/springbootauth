package com.kollu.angularone.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kollu.angularone.repository.UserModelRepository;

@Service
public class UserModelUserDetailsService implements UserDetailsService {

	@Autowired
	private UserModelRepository userModelRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		/* Optional<UserModel> userModel = userModelRepository.findByName(username);
		return userModel */
		return userModelRepository.findByName(username)
				.map(UserModelUserDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException("user not found " + username));
		
		/*
		 * // Convert comma-separated string "ROLE_ADMIN,ROLE_LEAD" into Authorities
		 * List<SimpleGrantedAuthority> authorities =
		 * Arrays.stream(userModel.getRoles().split(",")) .map(role -> new
		 * SimpleGrantedAuthority(role.trim())) .collect(Collectors.toList());
		 * 
		 * return new org.springframework.security.core.userdetails.User(
		 * userModel.getName(), userModel.getPassword(), authorities );
		 */
        
	}

}
