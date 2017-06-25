package com.bjss.baskettest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.rule.OutputCapture;

import com.bjss.baskettest.basket.model.Basket;
import com.bjss.baskettest.offer.OfferDetails;

@RunWith(MockitoJUnitRunner.class)
public class ConsoleOutputTests {

	private static final String NO_OFFERS = "Subtotal: £5.00\n(No offers available)\nTotal: £5.00\n";

	private static final String OFFERS = "Subtotal: £5.00\nOFFER_1: -£1.00\nOFFER_2: -£2.00\nTotal: £2.00\n";

	private static final BigDecimal BASKET_TOTAL = new BigDecimal(5);
	
	private ConsoleOutput outputter = new ConsoleOutput();
	
	@Mock
	Basket basket;
	
	@Rule
	public OutputCapture capture = new OutputCapture();

	@Before
	public void setup() {
		when(basket.getTotal()).thenReturn(BASKET_TOTAL);
	}
	
	@Test
	public void ConsoleOutputNoOffersTest() throws Exception {
		outputter.output(basket, new ArrayList<OfferDetails>());

		assertEquals(NO_OFFERS, capture.toString());
	}

	@Test
	public void ConsoleOutputOffersTest() throws Exception {
		outputter.output(basket, getOffers());

		assertEquals(OFFERS, capture.toString());
	}

	private List<OfferDetails> getOffers() {		
		OfferDetails offer1 = new OfferDetails();
		offer1.setOffer("OFFER_1");
		offer1.setDiscount(new BigDecimal(1));
		
		OfferDetails offer2 = new OfferDetails();
		offer2.setOffer("OFFER_2");
		offer2.setDiscount(new BigDecimal(2));
		
		return Arrays.asList(offer1, offer2);
	}
}