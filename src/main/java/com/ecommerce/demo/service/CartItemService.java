package com.ecommerce.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.demo.model.Cart;
import com.ecommerce.demo.model.CartItem;
import com.ecommerce.demo.model.Product;
import com.ecommerce.demo.repository.CartItemRepo;
import com.ecommerce.demo.repository.CartRepo;

@Service
public class CartItemService {
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CartRepo cartRepo;
	
	@Autowired
	private CartItemRepo cartItemRepo;

	public CartItem addItemToCart(Long cartId, Long productId, int quantity) {
		Cart cart = cartService.getCart(cartId);
		Product product = productService.getProductById(productId);
		CartItem cartItem = cart.getItems()
				.stream()
				.filter(item -> item.getProduct().getId() == (productId))
				.findFirst().orElse(new CartItem());
		if(cartItem.getId() == null) {
			cartItem.setCart(cart);
			cartItem.setProduct(product);
			cartItem.setQuantity(quantity);
			cartItem.setUnitPrice(product.getPrice());
		} else {
			cartItem.setQuantity(cartItem.getQuantity() + quantity);
		}
		
		cartItem.setTotalPrice();
		cart.addItem(cartItem);
		cartRepo.save(cart);
		//cartItemRepo.save(cartItem);
		return cartItem;
	}
}
