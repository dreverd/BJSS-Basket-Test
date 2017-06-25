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
public class BreadOfferTests {
	
	private static final String PRODUCT_APPLE = "Apples";
	private static final String PRODUCT_BREAD = "Bread";
	private static final String PRODUCT_SOUP = "Soup";
	
	private BreadOffer offer = new BreadOffer();
	
	private Map<Product, Integer> withBreadAndSoup;
	private Map<Product, Integer> withoutBreadAndSoup;
	private Map<Product, Integer> withMultipleBreadAndSoup;
	private Map<Product, Integer> withMultipleBread;
	private Map<Product, Integer> withMultipleSoup;

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

		withBreadAndSoup = new HashMap<Product, Integer>() {{put(apple, 1);put(bread, 1);put(soup, 2);}};
		withoutBreadAndSoup = new HashMap<Product, Integer>() {{put(apple, 1);}};
		withMultipleBreadAndSoup = new HashMap<Product, Integer>() {{put(apple, 1);put(bread, 2);put(soup, 4);}};
		withMultipleSoup = new HashMap<Product, Integer>() {{put(apple, 1);put(bread, 1);put(soup, 4);}};
		withMultipleBread = new HashMap<Product, Integer>() {{put(apple, 1);put(bread, 2);put(soup, 3);}};
	}
	
	@Test
	public void isApplicableTest() {
		when(data.getProducts()).thenReturn(withBreadAndSoup);
		assertTrue(offer.isApplicable(data));
	}

	@Test
	public void isNotApplicableTest() {
		when(data.getProducts()).thenReturn(withoutBreadAndSoup);
		assertFalse(offer.isApplicable(data));
	}	

	@Test
	public void isDiscountedTest() {
		when(data.getProducts()).thenReturn(withBreadAndSoup);
		OfferDetails offerDetails = offer.processData(data);
		
		assertEquals(BreadOffer.OFFER_DESCRIPTION, offerDetails.getOffer());
		assertEquals(new BigDecimal("0.40"), offerDetails.getDiscount());
	}

	@Test
	public void isDiscountedMultipleTimesTest() {
		when(data.getProducts()).thenReturn(withMultipleBreadAndSoup);
		OfferDetails offerDetails = offer.processData(data);
		
		assertEquals(BreadOffer.OFFER_DESCRIPTION, offerDetails.getOffer());
		assertEquals(new BigDecimal("0.80"), offerDetails.getDiscount());
	}

	@Test
	public void isDiscountLimitedByBreadTest() {
		when(data.getProducts()).thenReturn(withMultipleSoup);
		OfferDetails offerDetails = offer.processData(data);
		
		assertEquals(BreadOffer.OFFER_DESCRIPTION, offerDetails.getOffer());
		assertEquals(new BigDecimal("0.40"), offerDetails.getDiscount());
	}

	@Test
	public void isDiscountLimitedBySoupTest() {
		when(data.getProducts()).thenReturn(withMultipleBread);
		OfferDetails offerDetails = offer.processData(data);
		
		assertEquals(BreadOffer.OFFER_DESCRIPTION, offerDetails.getOffer());
		assertEquals(new BigDecimal("0.40"), offerDetails.getDiscount());
	}

	@Test
	public void isNotDiscounteTest() {
		when(data.getProducts()).thenReturn(withoutBreadAndSoup);
		OfferDetails offerDetails = offer.processData(data);
		
		assertEquals(BreadOffer.OFFER_DESCRIPTION, offerDetails.getOffer());
		assertEquals(new BigDecimal("0"), offerDetails.getDiscount());
	}	
}
