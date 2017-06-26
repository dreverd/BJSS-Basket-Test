package com.bjss.baskettest.basket;

import java.math.BigDecimal;
import java.util.Map;

import com.bjss.baskettest.product.model.Product;

/**
 * Signifies a product container
 */
public interface ProductAware {
	/**
	 * Return products in container
	 * @return map of products and counts
	 */
	public Map<Product, Integer> getProducts();
	
	/**
	 * Return total price of products in container
	 * @return
	 */
	public BigDecimal getTotal();
}
