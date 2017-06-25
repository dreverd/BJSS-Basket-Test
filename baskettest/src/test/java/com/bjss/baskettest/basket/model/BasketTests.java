package com.bjss.baskettest.basket.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.hamcrest.collection.IsMapContaining;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.bjss.baskettest.product.model.Product;

@RunWith(MockitoJUnitRunner.class)
public class BasketTests {
	
	private Basket basket = new Basket();
	
	@Mock
	Product apple, bread;

	@Before
	public void setup() {
		when(apple.getPrice()).thenReturn(new BigDecimal("1.00"));
		when(bread.getPrice()).thenReturn(new BigDecimal("0.80"));

	}
	
	@Test
	public void putProductInEmptyBasketTest() {
		basket.putProduct(apple);
		
		assertEquals(new BigDecimal("1.00"), basket.getTotal());
		assertEquals(basket.getProducts().size(), 1);
		assertThat(basket.getProducts(), IsMapContaining.hasEntry(apple, 1));
	}

	@Test
	public void putProductInBasketTest() {
		basket.putProduct(apple);
		basket.putProduct(bread);
		
		assertEquals(new BigDecimal("1.80"), basket.getTotal());
		assertEquals(basket.getProducts().size(), 2);
		assertThat(basket.getProducts(), IsMapContaining.hasEntry(apple, 1));
		assertThat(basket.getProducts(), IsMapContaining.hasEntry(bread, 1));
	}	

	@Test
	public void putRepeatedProductInBasketTest() {
		basket.putProduct(apple);
		basket.putProduct(bread);
		basket.putProduct(apple);
		
		assertEquals(new BigDecimal("2.80"), basket.getTotal());
		assertEquals(basket.getProducts().size(), 2);
		assertThat(basket.getProducts(), IsMapContaining.hasEntry(apple, 2));
		assertThat(basket.getProducts(), IsMapContaining.hasEntry(bread, 1));
	}
}
