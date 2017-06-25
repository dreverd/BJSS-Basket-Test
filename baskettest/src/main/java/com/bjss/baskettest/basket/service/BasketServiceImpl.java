package com.bjss.baskettest.basket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bjss.baskettest.basket.api.BasketService;
import com.bjss.baskettest.basket.exceptions.ProductNotFoundException;
import com.bjss.baskettest.basket.model.Basket;
import com.bjss.baskettest.product.api.ProductService;
import com.bjss.baskettest.product.model.Product;

/**
 * Service class for basket, provides link to products
 */
@Service
public class BasketServiceImpl implements BasketService {
	
	@Autowired
	private ProductService productService;

	@Autowired
	private Basket basket;
	
	@Override
	public Basket getBasket() {
		return basket;
	}

	@Override
	public void addToBasket(String productName) throws ProductNotFoundException {
		Product product = productService.getProduct(productName);
		
		if (product == null) {
			throw new ProductNotFoundException("No such product: " + productName);
		}
		
		getBasket().putProduct(product);
	}
}
