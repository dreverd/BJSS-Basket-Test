package com.bjss.baskettest.basket.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.bjss.baskettest.basket.api.BasketService;
import com.bjss.baskettest.basket.exceptions.ProductNotFoundException;
import com.bjss.baskettest.basket.model.Basket;
import com.bjss.baskettest.product.api.ProductService;
import com.bjss.baskettest.product.model.Product;

@RunWith(MockitoJUnitRunner.class)
public class BasketServiceTests {
	
	private static final String PRODUCT_TEA = "Tea";
	
	private static final String PRODUCT_APPLE = "Apples";
		
	@Mock
	private ProductService productService;

	@Mock
	private Basket basket;

	@Mock
	Product apple;
	
	@InjectMocks
	private BasketService basketService = new BasketServiceImpl();

	@Before
	public void setup() {
		when(productService.getProduct(PRODUCT_APPLE)).thenReturn(apple);		
		when(productService.getProduct(PRODUCT_TEA)).thenReturn(null);
	}
	
	@Test
	public void productNotFoundTest() {
		try {
			basketService.addToBasket(PRODUCT_TEA);
		} catch (ProductNotFoundException ex) {
			assertEquals("No such product: " + PRODUCT_TEA, ex.getMessage());
		};
	}

	@Test
	public void addToBasketTest() throws ProductNotFoundException {
		basketService.addToBasket(PRODUCT_APPLE);
		
		verify(basket).putProduct(apple);
	}
}
