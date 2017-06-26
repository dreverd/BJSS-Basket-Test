package com.bjss.baskettest.offer;

import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.HashMap;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.bjss.baskettest.basket.model.Basket;
import com.bjss.baskettest.offer.discounts.ApplesOffer;
import com.bjss.baskettest.offer.discounts.BreadOffer;
import com.bjss.baskettest.product.model.Product;

@RunWith(MockitoJUnitRunner.class)
public class BaseOfferProcessorTestSetup {
	
	protected ApplesOffer appleOffer = new ApplesOffer();
	protected BreadOffer breadOffer = new BreadOffer();

	protected static final String PRODUCT_APPLE = "Apples";
	protected static final String PRODUCT_BREAD = "Bread";
	protected static final String PRODUCT_SOUP = "Soup";

	@Mock
	protected Basket data;
	
	@Mock
	protected Product apple, bread, soup;

	@Before
	public void baseSetup() {
		when(apple.getName()).thenReturn(PRODUCT_APPLE);
		when(bread.getName()).thenReturn(PRODUCT_BREAD);
		when(soup.getName()).thenReturn(PRODUCT_SOUP);
		
		when(apple.getPrice()).thenReturn(new BigDecimal("1.00"));
		when(bread.getPrice()).thenReturn(new BigDecimal("0.80"));
		when(soup.getPrice()).thenReturn(new BigDecimal("0.60"));
	}
}
