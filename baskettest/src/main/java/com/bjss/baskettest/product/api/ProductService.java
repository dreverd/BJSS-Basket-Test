package com.bjss.baskettest.product.api;

import java.util.List;

import com.bjss.baskettest.product.model.Product;

public interface ProductService {
	public List<Product> getProducts();
	
	public Product getProduct(String name);

	public long addProduct(Product product);
}
