package com.ecommerce.demo.service;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.demo.exceptions.ResourceNotFoundException;
import com.ecommerce.demo.model.Cart;
import com.ecommerce.demo.repository.CartItemRepo;
import com.ecommerce.demo.repository.CartRepo;

@Service
public class CartService {
	
	@Autowired
	private CartRepo cartRepo;

	@Autowired
	private CartItemRepo cartItemRepo;
	
	private final AtomicLong cartIdGenerator = new AtomicLong(0);
	
	@Transactional
	public Long initializeNewCart() {
        Cart newCart = new Cart();
        Long newCartId = cartIdGenerator.incrementAndGet();
        newCart.setId(newCartId);
        return cartRepo.save(newCart).getId();
    }
	
	public Cart getCart(Long cartId) {
		Cart cart = cartRepo.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("No items in cart."));
		BigDecimal totalAmount = cart.getTotalAmount();
		cart.setTotalAmount(totalAmount);
		return cartRepo.save(cart);
	}
	
	@Transactional
    public void clearCart(Long id) {
        Cart cart = getCart(id);
        cartItemRepo.deleteAllByCartId(id);
        cart.getItems().clear();
        cartRepo.deleteById(id);

    }
	
    public BigDecimal getTotalPrice(Long id) {
        Cart cart = getCart(id);
        return cart.getTotalAmount();
    }
}
