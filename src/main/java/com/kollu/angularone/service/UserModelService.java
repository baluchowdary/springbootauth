package com.kollu.angularone.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kollu.angularone.model.UserModel;
import com.kollu.angularone.repository.UserModelRepository;

@Service
public class UserModelService {
	
	@Autowired
	private UserModelRepository userModelRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public List<UserModel> getAllUsers() {
		return userModelRepository.findAll();
	}

	public UserModel saveUser(UserModel user) {
		// Encrypt password before saving to DB
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		return userModelRepository.save(user);
	}

	public void deleteUser(Long id) {
		userModelRepository.deleteById(id);
	}
}
