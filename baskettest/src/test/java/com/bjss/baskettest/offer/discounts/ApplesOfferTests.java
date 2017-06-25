package com.bjss.baskettest.offer.discounts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.bjss.baskettest.basket.model.Basket;
import com.bjss.baskettest.offer.OfferDetails;
import com.bjss.baskettest.product.model.Product;

@RunWith(MockitoJUnitRunner.class)
public class ApplesOfferTests {
	
	private static final String PRODUCT_APPLE = "Apples";
	private static final String PRODUCT_BREAD = "Bread";
	private static final String PRODUCT_SOUP = "Soup";
	
	private ApplesOffer offer = new ApplesOffer();
	
	private Map<Product, Integer> withApple;
	private Map<Product, Integer> withoutApple;
	private Map<Product, Integer> withMultipleApple;

	@Mock
	Basket data;
	
	@Mock
	Product apple, bread, soup;

	@Before
	public void setup() {
		when(apple.getName()).thenReturn(PRODUCT_APPLE);
		when(bread.getName()).thenReturn(PRODUCT_BREAD);
		when(soup.getName()).thenReturn(PRODUCT_SOUP);
		
		when(apple.getPrice()).thenReturn(new BigDecimal("1.00"));
		when(bread.getPrice()).thenReturn(new BigDecimal("0.80"));
		when(soup.getPrice()).thenReturn(new BigDecimal("0.60"));

		withApple = new HashMap<Product, Integer>() {{put(apple, 1);put(bread, 1);}};
		withoutApple = new HashMap<Product, Integer>() {{put(soup, 1);put(bread, 1);}};
		withMultipleApple = new HashMap<Product, Integer>() {{put(apple, 2);put(bread, 1);}};
	}
	
	@Test
	public void isApplicableTest() {
		when(data.getProducts()).thenReturn(withApple);
		assertTrue(offer.isApplicable(data));
	}

	@Test
	public void isNotApplicableTest() {
		when(data.getProducts()).thenReturn(withoutApple);
		assertFalse(offer.isApplicable(data));
	}	

	@Test
	public void isDiscountedTest() {
		when(data.getProducts()).thenReturn(withApple);
		OfferDetails offerDetails = offer.processData(data);
		
		assertEquals(ApplesOffer.OFFER_DESCRIPTION, offerDetails.getOffer());
		assertEquals(new BigDecimal("0.10"), offerDetails.getDiscount());
	}

	@Test
	public void isDiscountedMultipleTimesTest() {
		when(data.getProducts()).thenReturn(withMultipleApple);
		OfferDetails offerDetails = offer.processData(data);
		
		assertEquals(ApplesOffer.OFFER_DESCRIPTION, offerDetails.getOffer());
		assertEquals(new BigDecimal("0.20"), offerDetails.getDiscount());
	}

	@Test
	public void isNotDiscounteTest() {
		when(data.getProducts()).thenReturn(withoutApple);
		OfferDetails offerDetails = offer.processData(data);
		
		assertEquals(ApplesOffer.OFFER_DESCRIPTION, offerDetails.getOffer());
		assertEquals(new BigDecimal("0"), offerDetails.getDiscount());
	}	
}
