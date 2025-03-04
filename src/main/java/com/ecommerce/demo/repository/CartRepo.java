package com.ecommerce.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.demo.model.Cart;

@Repository
public interface CartRepo extends JpaRepository<Cart, Long>{

}
