package com.commerce.toy.domain.user.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.commerce.toy.domain.user.User;

public interface UserRepository extends JpaRepository<User, Long> {
	boolean existsUserByEmail(String email);
}
