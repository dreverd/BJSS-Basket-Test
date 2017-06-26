package com.bjss.baskettest.offer;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.bjss.baskettest.offer.discounts.ApplesOffer;
import com.bjss.baskettest.offer.discounts.BreadOffer;
import com.bjss.baskettest.product.model.Product;

@RunWith(MockitoJUnitRunner.class)
public class OfferProcessorTests extends BaseOfferProcessorTestSetup {
	
	private Map<Product, Integer> noMatch;
	private Map<Product, Integer> noProducts;
	private Map<Product, Integer> matchFirstOffer;
	private Map<Product, Integer> matchSecondOffer;
	private Map<Product, Integer> matchAllOffers;
	
	@Before
	public void setup() {
		noProducts = new HashMap<Product, Integer>();
		noMatch = new HashMap<Product, Integer>() {{put(bread, 1);}};
		matchFirstOffer = new HashMap<Product, Integer>() {{put(apple, 1);put(bread, 1);}};
		matchSecondOffer = new HashMap<Product, Integer>() {{put(soup, 2);put(bread, 1);}};
		matchAllOffers = new HashMap<Product, Integer>() {{put(apple, 1);put(soup, 2);put(bread, 1);}};
	}

	@Test
	public void nullBasketTest() {
		List<OfferDetails> offers = appleOffer.process(null);		
		
		assertEquals(0, offers.size());		
	}

	@Test
	public void emptyBasketTest() {
		when(data.getProducts()).thenReturn(noProducts);
		List<OfferDetails> offers = appleOffer.process(data);		
		
		assertEquals(0, offers.size());
	}

	@Test
	public void noMatchingOfferTest() {
		when(data.getProducts()).thenReturn(noMatch);
		List<OfferDetails> offers = appleOffer.process(data);		
		
		assertEquals(0, offers.size());
	}

	@Test
	public void noMatchingMultipleOfferTest() {
		when(data.getProducts()).thenReturn(noMatch);
		appleOffer.setNextProcessor(breadOffer);		
		List<OfferDetails> offers = appleOffer.process(data);		
		
		assertEquals(0, offers.size());
	}

	@Test
	public void singleOfferMatchTest() {
		// basket is passed in with single offer with match
		when(data.getProducts()).thenReturn(matchFirstOffer);
		List<OfferDetails> offers = appleOffer.process(data);		

		assertEquals(1, offers.size());
		assertEquals(ApplesOffer.OFFER_DESCRIPTION, offers.get(0).getOffer());
		assertEquals(new BigDecimal("0.10"), offers.get(0).getDiscount());
	}

	@Test
	public void multipleOfferMatchFirstTest() {
		when(data.getProducts()).thenReturn(matchFirstOffer);
		appleOffer.setNextProcessor(breadOffer);		
		List<OfferDetails> offers = appleOffer.process(data);		
		
		assertEquals(1, offers.size());
		assertEquals(ApplesOffer.OFFER_DESCRIPTION, offers.get(0).getOffer());
		assertEquals(new BigDecimal("0.10"), offers.get(0).getDiscount());
	}

	@Test
	public void multipleOfferMatchLastTest() {
		when(data.getProducts()).thenReturn(matchSecondOffer);
		appleOffer.setNextProcessor(breadOffer);		
		List<OfferDetails> offers = appleOffer.process(data);		
		
		assertEquals(1, offers.size());
		assertEquals(BreadOffer.OFFER_DESCRIPTION, offers.get(0).getOffer());
		assertEquals(new BigDecimal("0.40"), offers.get(0).getDiscount());
	}

	@Test
	public void multipleOfferMatchAllTest() {
		when(data.getProducts()).thenReturn(matchAllOffers);
		appleOffer.setNextProcessor(breadOffer);		
		List<OfferDetails> offers = appleOffer.process(data);		
		
		assertEquals(2, offers.size());
		
		assertEquals(ApplesOffer.OFFER_DESCRIPTION, offers.get(0).getOffer());
		assertEquals(new BigDecimal("0.10"), offers.get(0).getDiscount());
		
		assertEquals(BreadOffer.OFFER_DESCRIPTION, offers.get(1).getOffer());
		assertEquals(new BigDecimal("0.40"), offers.get(1).getDiscount());
	}
}
