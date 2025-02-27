package com.ecommerce.demo.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Cart {
	public Cart() {
		super();
	}

	public Cart(Long id, BigDecimal totalAmount, Set<CartItem> items, User user) {
		super();
		this.id = id;
		this.totalAmount = totalAmount;
		this.items = items;
		this.user = user;
	}

	@Id
	private Long id;
	private BigDecimal totalAmount = BigDecimal.ZERO;
	
	@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartItem> items = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Set<CartItem> getItems() {
		return items;
	}

	public void setItems(Set<CartItem> items) {
		this.items = items;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void addItem(CartItem item) {
		this.items.add(item);
		item.setCart(this);
		updateTotalAmount();
	}
	
	public void updateTotalAmount() {
		this.totalAmount = items.stream().
				map(item -> {
					BigDecimal unitPrice = item.getUnitPrice();
					if(unitPrice == null) {
						return BigDecimal.ZERO;
					}
					return unitPrice.multiply(BigDecimal.valueOf(item.getQuantity()));
				}).reduce(BigDecimal.ZERO, BigDecimal::add);
	}
	
	public void removeItem(CartItem item) {
		this.items.remove(item);
		item.setCart(null);
		updateTotalAmount();
	}
}
