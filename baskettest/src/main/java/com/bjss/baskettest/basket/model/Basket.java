package com.bjss.baskettest.basket.model;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.bjss.baskettest.basket.ProductAware;
import com.bjss.baskettest.product.model.Product;

@Component
public class Basket implements ProductAware {
	private Map<Product, Integer> products = new HashMap<>();
	
	private BigDecimal total;
	
	/**
	 * Add product to basket and re-calculate total
	 * @param product to add
	 */
	public void putProduct(Product product) {
		products.merge(product, 1, Integer::sum);
		total = calculateTotal();
	}
	
	@Override
	public Map<Product, Integer> getProducts() {
		return Collections.unmodifiableMap(products);
	}

	@Override
	public BigDecimal getTotal() {
		return total;
	}

	/**
	 * Calculate total price, sum of product price * product count 
	 * @return total price of products
	 */
	private BigDecimal calculateTotal() {
		return products
				.entrySet()
				.stream()
				.map(e -> e.getKey().getPrice().multiply(new BigDecimal(e.getValue())))
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}
}
