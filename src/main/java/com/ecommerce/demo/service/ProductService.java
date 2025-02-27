package com.ecommerce.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.demo.exceptions.ProductNotFoundException;
import com.ecommerce.demo.model.Product;
import com.ecommerce.demo.repository.ProductRepo;

@Service
public class ProductService {
	//List<Product> products = new ArrayList<>(Arrays.asList(new Product(1, "Brush", "color: Blue, size: 10cm", 45), new Product(2, "Paste", "brand: closeup", 87)));

	@Autowired
	private ProductRepo repo;
	
	public List<Product> getProducts() {
		return repo.findAll();
	}
	
	public Product getProductById(int prodId) {
		return repo.findById(prodId).orElseThrow(() -> new ProductNotFoundException("Product not found!"));
	}
	
	public Product addProduct(Product product) {
		return repo.save(product);
	}

	public void updateProduct(Product p) {
		/*
		 * if(repo.findById(p.getId()).get() != null) return repo.save(p);
		 * 
		 * throw new ProductNotFoundException("Product not found to update!");
		 */
		
		repo.findById(p.getId()).ifPresentOrElse(repo::save, () -> {throw new ProductNotFoundException("Product not present to update!");});
		//return repo.save(p);
	}

	public void delProductById(int prodId) {
		repo.findById(prodId).ifPresentOrElse(repo::delete, () -> {throw new ProductNotFoundException("Product not present to delete!");});
	}

	public List<Product> getProductByName(String keyword) {
		return repo.findByKeyword(keyword);
	}
}
