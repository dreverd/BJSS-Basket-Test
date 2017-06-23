package com.bjss.baskettest.basket.model;

import java.util.List;

import com.bjss.baskettest.product.model.Product;

public class Basket {
	private List<Product> products;

	/**
	 * @return the products
	 */
	public List<Product> getProducts() {
		return products;
	}

	/**
	 * @param products the products to set
	 */
	public void setProducts(List<Product> products) {
		this.products = products;
	}
}
