package com.bjss.baskettest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.bjss.baskettest.basket.api.BasketService;
import com.bjss.baskettest.basket.exceptions.ProductNotFoundException;
import com.bjss.baskettest.basket.model.Basket;
import com.bjss.baskettest.offer.OfferDetails;
import com.bjss.baskettest.offer.OfferFactory;
import com.bjss.baskettest.offer.OfferProcessor;

@RunWith(MockitoJUnitRunner.class)
public class PriceBasketTests {
	
	private static final String NO_PRODUCTS = "No items to add to basket";
	
	private static final String PRODUCT_TEA = "tea";
	private static final String PRODUCT_APPLE = "apple";

	private static final String NO_PRODUCT_TEA = "No such product: " + PRODUCT_TEA;	
	
	@Mock
	private BasketService basketService;

	@Mock
	Basket data;
	
	@Mock
	private OfferFactory offers;
	
	@Mock
	ConsoleOutput outputter;

	@Mock
	OfferProcessor offer;
	
	@Mock
	List<OfferDetails> offerDetails;
	
    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    @InjectMocks
    private PriceBasket priceBasket = new PriceBasket();
	
	@Test
	public void noArgsTest() throws Exception {
	    exit.expectSystemExit();
	    exit.checkAssertionAfterwards(() -> assertEquals(NO_PRODUCTS, systemOutRule.getLog()));
	    priceBasket.run(new String[]{});
	}

	@Test
	public void productNotFoundTest() throws Exception {
		doThrow(new ProductNotFoundException(NO_PRODUCT_TEA)).when(basketService).addToBasket(PRODUCT_TEA);
		
	    exit.expectSystemExit();
	    exit.checkAssertionAfterwards(() -> {verify(basketService).addToBasket(PRODUCT_TEA);
	    									 assertEquals(NO_PRODUCT_TEA, systemOutRule.getLog());
	    									 verify(offers, never()).getLeadOffer();});
	    priceBasket.run(new String[]{PRODUCT_TEA});
	}

	@Test
	public void productFoundTest() throws Exception {
		when(basketService.getBasket()).thenReturn(data);
		when(offers.getLeadOffer()).thenReturn(offer);
		when(offer.process(data)).thenReturn(offerDetails);
		
	    exit.expectSystemExit();
	    exit.checkAssertionAfterwards(() -> {verify(basketService, times(1)).addToBasket(PRODUCT_APPLE);
										     verify(basketService, times(1)).addToBasket(anyString());
										     verify(offers).getLeadOffer();
										     verify(offer).process(data);
	    									 verify(outputter).output(data, offerDetails);});
	    priceBasket.run(new String[]{PRODUCT_APPLE});
	}
}
