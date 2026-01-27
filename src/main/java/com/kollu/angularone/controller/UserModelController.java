package com.kollu.angularone.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kollu.angularone.model.UserModel;
import com.kollu.angularone.service.UserModelService;

@RestController
@RequestMapping("/users")
public class UserModelController {
	@Autowired
	private UserModelService userModelService;

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
}
