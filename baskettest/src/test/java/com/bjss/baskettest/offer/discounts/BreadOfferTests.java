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
import org.mockito.runners.MockitoJUnitRunner;

import com.bjss.baskettest.offer.BaseOfferProcessorTestSetup;
import com.bjss.baskettest.offer.OfferDetails;
import com.bjss.baskettest.product.model.Product;

@RunWith(MockitoJUnitRunner.class)
public class BreadOfferTests extends BaseOfferProcessorTestSetup {
	
	private Map<Product, Integer> withBreadAndSoup;
	private Map<Product, Integer> withoutBreadAndSoup;
	private Map<Product, Integer> withMultipleBreadAndSoup;
	private Map<Product, Integer> withMultipleBread;
	private Map<Product, Integer> withMultipleSoup;

	@Before
	public void setup() {
		withBreadAndSoup = new HashMap<Product, Integer>() {{put(apple, 1);put(bread, 1);put(soup, 2);}};
		withoutBreadAndSoup = new HashMap<Product, Integer>() {{put(apple, 1);}};
		withMultipleBreadAndSoup = new HashMap<Product, Integer>() {{put(apple, 1);put(bread, 2);put(soup, 4);}};
		withMultipleSoup = new HashMap<Product, Integer>() {{put(apple, 1);put(bread, 1);put(soup, 4);}};
		withMultipleBread = new HashMap<Product, Integer>() {{put(apple, 1);put(bread, 2);put(soup, 3);}};
	}
	
	@Test
	public void isApplicableTest() {
		when(data.getProducts()).thenReturn(withBreadAndSoup);
		assertTrue(breadOffer.isApplicable(data));
	}

	@Test
	public void isNotApplicableTest() {
		when(data.getProducts()).thenReturn(withoutBreadAndSoup);
		assertFalse(breadOffer.isApplicable(data));
	}	

	@Test
	public void isDiscountedTest() {
		when(data.getProducts()).thenReturn(withBreadAndSoup);
		OfferDetails offerDetails = breadOffer.processData(data);
		
		assertEquals(BreadOffer.OFFER_DESCRIPTION, offerDetails.getOffer());
		assertEquals(new BigDecimal("0.40"), offerDetails.getDiscount());
	}

	@Test
	public void isDiscountedMultipleTimesTest() {
		when(data.getProducts()).thenReturn(withMultipleBreadAndSoup);
		OfferDetails offerDetails = breadOffer.processData(data);
		
		assertEquals(BreadOffer.OFFER_DESCRIPTION, offerDetails.getOffer());
		assertEquals(new BigDecimal("0.80"), offerDetails.getDiscount());
	}

	@Test
	public void isDiscountLimitedByBreadTest() {
		when(data.getProducts()).thenReturn(withMultipleSoup);
		OfferDetails offerDetails = breadOffer.processData(data);
		
		assertEquals(BreadOffer.OFFER_DESCRIPTION, offerDetails.getOffer());
		assertEquals(new BigDecimal("0.40"), offerDetails.getDiscount());
	}

	@Test
	public void isDiscountLimitedBySoupTest() {
		when(data.getProducts()).thenReturn(withMultipleBread);
		OfferDetails offerDetails = breadOffer.processData(data);
		
		assertEquals(BreadOffer.OFFER_DESCRIPTION, offerDetails.getOffer());
		assertEquals(new BigDecimal("0.40"), offerDetails.getDiscount());
	}

	@Test
	public void isNotDiscounteTest() {
		when(data.getProducts()).thenReturn(withoutBreadAndSoup);
		OfferDetails offerDetails = breadOffer.processData(data);
		
		assertEquals(BreadOffer.OFFER_DESCRIPTION, offerDetails.getOffer());
		assertEquals(new BigDecimal("0"), offerDetails.getDiscount());
	}	
}
