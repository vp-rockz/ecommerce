package com.ecommerce.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.demo.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	boolean existsByEmail(String email);
}
