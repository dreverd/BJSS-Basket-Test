package com.bjss.baskettest.offer;

import org.springframework.stereotype.Component;

import com.bjss.baskettest.offer.discounts.ApplesOffer;
import com.bjss.baskettest.offer.discounts.BreadOffer;

/**
 * Simple factory to create and chain offers 
 */
@Component
public class OfferFactory {
	
	private ApplesOffer leadOffer;
	
	public OfferFactory() {
		leadOffer = new ApplesOffer();
		
		leadOffer.setNextProcessor(new BreadOffer());
	}
	
	public OfferProcessor getLeadOffer() {
		return leadOffer;
	}
}
