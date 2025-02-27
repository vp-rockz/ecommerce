package com.ecommerce.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.demo.model.CartItem;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem, Long>{
	void deleteAllByCartId(Long id);
}
