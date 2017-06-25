package com.bjss.baskettest.offer.discounts;

import java.math.BigDecimal;
import java.util.Map.Entry;
import java.util.Optional;

import com.bjss.baskettest.basket.ProductAware;
import com.bjss.baskettest.offer.OfferDetails;
import com.bjss.baskettest.offer.OfferProcessor;
import com.bjss.baskettest.product.model.Product;

public class BreadOffer extends OfferProcessor {
	private static final String PRODUCT_MATCH_SOUP = "Soup";

	private static final String PRODUCT_MATCH_BREAD = "Bread";
	
	public static final String OFFER_DESCRIPTION = "Buy 2 tins of soup, get loaf half price";
	
	public boolean isApplicable(ProductAware data) {
		Optional<Entry<Product, Integer>> soupEntry = getEntry(data, PRODUCT_MATCH_SOUP);
		Optional<Entry<Product, Integer>> breadEntry = getEntry(data, PRODUCT_MATCH_BREAD);
		
		return breadEntry.isPresent() && soupEntry.isPresent() && soupEntry.get().getValue() >= 2;
	}
	
	@Override
	protected OfferDetails processData(ProductAware data) {
		Optional<Entry<Product, Integer>> soupEntry = getEntry(data, PRODUCT_MATCH_SOUP);
		Optional<Entry<Product, Integer>> breadEntry = getEntry(data, PRODUCT_MATCH_BREAD);

		if (soupEntry.isPresent() && breadEntry.isPresent()) {
			int maxDiscounts = soupEntry.get().getValue() / 2;
			int numBreadEntries = breadEntry.get().getValue();
			int numDiscounts = maxDiscounts > numBreadEntries ? numBreadEntries : maxDiscounts;

			BigDecimal price = breadEntry.get().getKey().getPrice();
						
			offerDetails.setDiscount(price.multiply(new BigDecimal(numDiscounts))
										  .divide(new BigDecimal(2)));
		}
		
		offerDetails.setOffer(OFFER_DESCRIPTION);
		
	    return offerDetails;
	}
}
