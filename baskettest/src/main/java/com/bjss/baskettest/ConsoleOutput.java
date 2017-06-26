package com.bjss.baskettest;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;

import org.springframework.stereotype.Component;

import com.bjss.baskettest.basket.model.Basket;
import com.bjss.baskettest.offer.OfferDetails;

/**
 * Handle output for application, displays:
 * 		Subtotal - sum of product prices
 * 		Discounts - sum of individual discounts
 *		Total - subtotal minus discounts
 */
@Component
public class ConsoleOutput {

	public void output(Basket basket, List<OfferDetails> offers) {
		 BigDecimal subtotal = basket.getTotal();
		 BigDecimal totalDiscounts = BigDecimal.ZERO;
		 BigDecimal total = subtotal;

		 System.out.printf("%s%s\n", "Subtotal: ", NumberFormat.getCurrencyInstance().format(subtotal));
		 
		 if (offers == null || offers.isEmpty()) {
			 System.out.printf("%s\n", "(No offers available)");			 
		 } else {
			 totalDiscounts = offers.stream().map(o -> o.getDiscount()).reduce(BigDecimal.ZERO, BigDecimal::add);
			 total = subtotal.subtract(totalDiscounts);
			 offers.forEach(o -> System.out.printf("%s%s%s\n", o.getOffer(), ": -", NumberFormat.getCurrencyInstance().format(o.getDiscount())));
		 }

		 System.out.printf("%s%s\n", "Total: ", NumberFormat.getCurrencyInstance().format(total));
	}
}
