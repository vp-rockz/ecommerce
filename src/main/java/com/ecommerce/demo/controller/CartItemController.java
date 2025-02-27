package com.ecommerce.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.demo.exceptions.ResourceNotFoundException;
import com.ecommerce.demo.model.CartItem;
import com.ecommerce.demo.response.ApiResponse;
import com.ecommerce.demo.service.CartItemService;
import com.ecommerce.demo.service.CartService;

@RestController
@RequestMapping("/api/cartItems")
public class CartItemController {
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private CartItemService cartItemService;
	
	@PostMapping("/item/add")
	public ResponseEntity<ApiResponse> addItemToCart(@RequestParam(required = false) Long cartId,
			@RequestParam Long prodId,
			@RequestParam int quantity) {
		try {
			if(cartId == null) {
				cartId = cartService.initializeNewCart();
			}
			CartItem ci = cartItemService.addItemToCart(cartId, prodId, quantity);
			return ResponseEntity.ok(new ApiResponse("Item added successfully to cart " + ci.getCart().getId(), ci.getCart()));
		} catch(ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(e.getMessage(), null));
		}
	}

}
