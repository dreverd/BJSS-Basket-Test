package com.bjss.baskettest.offer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;

import com.bjss.baskettest.basket.ProductAware;
import com.bjss.baskettest.product.model.Product;

/**
 * An implementation of the chain of responsibility pattern to handle
 * offers, each offer is processed and then calls next in chain. The results
 * are passed back up the chain to the caller. 
 * 
 */
public abstract class OfferProcessor {
    protected OfferProcessor next;
    
	protected OfferDetails offerDetails = new OfferDetails();

	private List<OfferDetails> offers = new ArrayList<>();

	/**
	 * Add next offer to chain
	 * @param processor offer
	 */
    public void setNextProcessor(OfferProcessor processor) {
        this.next = processor;
    }
    
    /**
     * Test the offer to see if it can be applied to the basket, then
     * call the next offer in the chain.
     * @param data product container (basket)
     * @return details of offers in the chain below the current offer
     */
    public List<OfferDetails> process(ProductAware data) {
    	if (data != null && !data.getProducts().isEmpty()) {
	        if (isApplicable(data)) {
	        	offers.add(processData(data));
	        }
	        
	        if (next != null) {
	        	offers.addAll(next.process(data));
	        }
    	}
        
        return offers;
    }
    
    /**
     * Find the entry in the container for the named product, that entry contains
     * the product and a product count.
     * @param data product container (basket)
     * @param name of product
     * @return Entry containing product and count
     */
    protected Optional<Entry<Product, Integer>> getEntry(ProductAware data, String name) {
		return data.getProducts().entrySet()
				.stream()
				.filter(e -> e.getKey().getName().equals(name)).findFirst();
    }
    
    /**
     * Test if the offer can be applied to the product container
     * @param data product container (basket)
     * @return true if applicable
     */
    abstract protected boolean isApplicable(ProductAware data);

    /**
     * Process the offer and calculate any discount
     * @param data product container (basket)
     * @return details of any discount
     */
    abstract protected OfferDetails processData(ProductAware data);
}
