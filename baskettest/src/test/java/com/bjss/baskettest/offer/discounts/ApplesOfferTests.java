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
public class ApplesOfferTests extends BaseOfferProcessorTestSetup {
	
	private Map<Product, Integer> withApple;
	private Map<Product, Integer> withoutApple;
	private Map<Product, Integer> withMultipleApple;

	@Before
	public void setup() {
		withApple = new HashMap<Product, Integer>() {{put(apple, 1);put(bread, 1);}};
		withoutApple = new HashMap<Product, Integer>() {{put(soup, 1);put(bread, 1);}};
		withMultipleApple = new HashMap<Product, Integer>() {{put(apple, 2);put(bread, 1);}};
	}
	
	@Test
	public void isApplicableTest() {
		when(data.getProducts()).thenReturn(withApple);
		assertTrue(appleOffer.isApplicable(data));
	}

	@Test
	public void isNotApplicableTest() {
		when(data.getProducts()).thenReturn(withoutApple);
		assertFalse(appleOffer.isApplicable(data));
	}	

	@Test
	public void isDiscountedTest() {
		when(data.getProducts()).thenReturn(withApple);
		OfferDetails offerDetails = appleOffer.processData(data);
		
		assertEquals(ApplesOffer.OFFER_DESCRIPTION, offerDetails.getOffer());
		assertEquals(new BigDecimal("0.10"), offerDetails.getDiscount());
	}

	@Test
	public void isDiscountedMultipleTimesTest() {
		when(data.getProducts()).thenReturn(withMultipleApple);
		OfferDetails offerDetails = appleOffer.processData(data);
		
		assertEquals(ApplesOffer.OFFER_DESCRIPTION, offerDetails.getOffer());
		assertEquals(new BigDecimal("0.20"), offerDetails.getDiscount());
	}

	@Test
	public void isNotDiscounteTest() {
		when(data.getProducts()).thenReturn(withoutApple);
		OfferDetails offerDetails = appleOffer.processData(data);
		
		assertEquals(ApplesOffer.OFFER_DESCRIPTION, offerDetails.getOffer());
		assertEquals(new BigDecimal("0"), offerDetails.getDiscount());
	}	
}
