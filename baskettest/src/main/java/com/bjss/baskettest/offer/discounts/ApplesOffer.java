package com.bjss.baskettest.offer.discounts;

import java.math.BigDecimal;
import java.util.Map.Entry;
import java.util.Optional;

import com.bjss.baskettest.basket.ProductAware;
import com.bjss.baskettest.offer.OfferDetails;
import com.bjss.baskettest.offer.OfferProcessor;
import com.bjss.baskettest.product.model.Product;

public class ApplesOffer extends OfferProcessor {
	private static final String PRODUCT_MATCH_APPLES = "Apples";

	public static final String OFFER_DESCRIPTION = "Apples 10% off";
	
	public boolean isApplicable(ProductAware data) {
		return data.getProducts().keySet().stream().anyMatch(product -> product.getName().equals(PRODUCT_MATCH_APPLES));
	}
	
	@Override
	protected OfferDetails processData(ProductAware data) {
		Optional<Entry<Product, Integer>> entry = getEntry(data, PRODUCT_MATCH_APPLES);
		
		if (entry.isPresent()) {
	    	BigDecimal price = entry.get().getKey().getPrice();
	    	BigDecimal count = new BigDecimal(entry.get().getValue());
	    	offerDetails.setDiscount(price.multiply(count).movePointLeft(1).setScale(2));
		}
		
		offerDetails.setOffer(OFFER_DESCRIPTION);
		
	    return offerDetails;
	}
}
