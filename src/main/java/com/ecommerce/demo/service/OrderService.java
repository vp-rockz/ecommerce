package com.ecommerce.demo.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.demo.exceptions.ResourceNotFoundException;
import com.ecommerce.demo.model.Cart;
import com.ecommerce.demo.model.Order;
import com.ecommerce.demo.model.OrderItem;
import com.ecommerce.demo.model.Product;
import com.ecommerce.demo.repository.OrderRepo;
import com.ecommerce.demo.repository.ProductRepo;
import com.ecommerce.demo.vo.OrderStatus;

@Service
public class OrderService {

	@Autowired
	private ProductRepo productRepo;

	@Autowired
	private OrderRepo orderRepo;

	@Autowired
	private CartService cartService;

	public Order placeOrder(Long userId) {
		Cart cart = cartService.getCart(userId);
		Order order = createOrder(cart);
		List<OrderItem> orderItemList = createOrderItems(order, cart);
		order.setOrderItems(new HashSet<>(orderItemList));
		order.setTotalAmount(calculateTotalAmount(orderItemList));
		Order savedOrder = orderRepo.save(order);
		cartService.clearCart(cart.getId());
		return savedOrder;
	}

	private Order createOrder(Cart cart) {
		Order order = new Order();
		order.setUser(cart.getUser());
		order.setOrderStatus(OrderStatus.PLACED);
		order.setOrderDate(LocalDate.now());
		return order;
	}

	private List<OrderItem> createOrderItems(Order order, Cart cart) {
		return cart.getItems().stream().map(cartItem -> {
			Product product = cartItem.getProduct();
			product.setQuantity(product.getQuantity() - cartItem.getQuantity());
			productRepo.save(product);
			return new OrderItem(order, product, cartItem.getQuantity(), cartItem.getUnitPrice());
		}).toList();

	}

	private BigDecimal calculateTotalAmount(List<OrderItem> orderItemList) {
		return orderItemList.stream().map(item -> item.getPrice().multiply(new BigDecimal(item.getQuantity())))
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public Order getOrder(Long orderId) {
        return orderRepo.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

}
