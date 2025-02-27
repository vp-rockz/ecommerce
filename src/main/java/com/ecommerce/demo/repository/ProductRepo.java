package com.ecommerce.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ecommerce.demo.model.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long>{
	
	@Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
	"OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
			"OR LOWER(p.category) LIKE LOWER(CONCAT('%', :keyword, '%'))")
	public List<Product> findByKeyword(String keyword);

}
