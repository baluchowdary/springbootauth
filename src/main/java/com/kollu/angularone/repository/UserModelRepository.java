package com.kollu.angularone.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kollu.angularone.model.UserModel;

public interface UserModelRepository extends JpaRepository<UserModel, Long> {
	Optional<UserModel> findByName(String username);

}