package com.commerce.toy.domain.user.service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.commerce.toy.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	boolean existsUserById(String id);

	Optional<User> findUserById(String id);
}
