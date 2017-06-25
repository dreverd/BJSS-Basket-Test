package com.bjss.baskettest.offer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;

import com.bjss.baskettest.basket.ProductAware;
import com.bjss.baskettest.product.model.Product;

public abstract class OfferProcessor {
    protected OfferProcessor next;
    
	protected OfferDetails offerDetails = new OfferDetails();

	private List<OfferDetails> offers = new ArrayList<>();

    public void setNextProcessor(OfferProcessor processor) {
        this.next = processor;
    }
    
    public List<OfferDetails> process(ProductAware data) {
        if (isApplicable(data)) {
        	offers.add(processData(data));
        }
        
        if (next != null) {
        	offers.addAll(next.process(data));
        }
        
        return offers;
    }
    
    protected Optional<Entry<Product, Integer>> getEntry(ProductAware data, String name) {
		return data.getProducts().entrySet()
				.stream()
				.filter(e -> e.getKey().getName().equals(name)).findFirst();
    }
    
    abstract protected boolean isApplicable(ProductAware data);

    abstract protected OfferDetails processData(ProductAware data);
}
