package com.bjss.baskettest.basket;

import java.math.BigDecimal;
import java.util.Map;

import com.bjss.baskettest.product.model.Product;

public interface ProductAware {
	public Map<Product, Integer> getProducts();
	
	public BigDecimal getTotal();
}
