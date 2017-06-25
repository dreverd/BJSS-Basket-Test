package com.bjss.baskettest.offer;

import java.math.BigDecimal;

public class OfferDetails {
	private String offer;
	
	private BigDecimal discount = BigDecimal.ZERO;

	/**
	 * @return the offer
	 */
	public String getOffer() {
		return offer;
	}

	/**
	 * @param offer the offer to set
	 */
	public void setOffer(String offer) {
		this.offer = offer;
	}

	/**
	 * @return the discount
	 */
	public BigDecimal getDiscount() {
		return discount;
	}

	/**
	 * @param discount the discount to set
	 */
	public void setDiscount(BigDecimal discount) {
		this.discount = this.discount.add(discount);
	}
}
