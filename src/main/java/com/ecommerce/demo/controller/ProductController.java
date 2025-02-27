package com.ecommerce.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.demo.exceptions.ProductNotFoundException;
import com.ecommerce.demo.model.Product;
import com.ecommerce.demo.response.ApiResponse;
import com.ecommerce.demo.service.ProductService;

@RestController
@RequestMapping("/api")
public class ProductController {

	@Autowired
	private ProductService service;

	@GetMapping("/products")
	public ResponseEntity<List<Product>> getAllProducts() {
		return new ResponseEntity<>(service.getProducts(), HttpStatus.OK);
	}

	@GetMapping("/product/{prodId}")
	public ResponseEntity<ApiResponse> getProductsById(@PathVariable Long prodId) {
		try {
			Product product = service.getProductById(prodId);
			return ResponseEntity.ok(new ApiResponse("Success", product));
		} catch (ProductNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(e.getMessage(), "ProductID: " + prodId + " not found"));
		}
	}

	@PostMapping("/product")
	public ResponseEntity<ApiResponse> addProduct(@RequestBody Product p) {
		return ResponseEntity.ok(new ApiResponse("Success", service.addProduct(p)));
		// return new ResponseEntity <>(service.addProduct(p), HttpStatus.OK);
	}

	@PutMapping("/product")
	public ResponseEntity<ApiResponse> updateProduct(@RequestBody Product p) {
		try {
			service.updateProduct(p);
			return ResponseEntity.ok(new ApiResponse("Success", null));
		} catch (ProductNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(e.getMessage(), "ProductID: " + p.getId() + " not present to update."));
		}
	}

	@DeleteMapping("/product/{prodId}")
	public ResponseEntity<ApiResponse> delProductByName(@PathVariable Long prodId) {
		try {
			service.delProductById(prodId);
			return ResponseEntity.ok(new ApiResponse("Success", null));
		} catch (ProductNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(e.getMessage(), "ProductID: " + prodId + " not found to delete."));
		}
	}

	@GetMapping("/product/search/{keyword}")
	public ResponseEntity<ApiResponse> searchProduct(@PathVariable String keyword) {
		try {
			List<Product> p = service.getProductByName(keyword);
			if (!ObjectUtils.isEmpty(p)) {
				return ResponseEntity.ok(new ApiResponse("Success", p));
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Not Found", keyword + " doesn't match to any available products!"));
			}
		} catch (ProductNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(e.getMessage(), null));
		}
	}
}
