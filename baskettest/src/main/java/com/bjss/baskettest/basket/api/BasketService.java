package com.bjss.baskettest.basket.api;

import com.bjss.baskettest.basket.exceptions.ProductNotFoundException;
import com.bjss.baskettest.basket.model.Basket;

public interface BasketService {
	/*
	 * Return a basket
	 */
	public Basket getBasket();
	
	/**
	 * Find product by name and add to basket
	 * @param product name of product
	 * @throws ProductNotFoundException
	 */
	public void addToBasket(String product) throws ProductNotFoundException;
}
