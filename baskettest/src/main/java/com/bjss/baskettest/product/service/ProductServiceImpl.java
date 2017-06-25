package com.bjss.baskettest.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bjss.baskettest.product.api.ProductService;
import com.bjss.baskettest.product.model.Product;
import com.bjss.baskettest.product.repository.ProductRepository;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public List<Product> getProducts() {
		return productRepository.findAll();
	}

	@Override
	public long addProduct(Product product) {
		return productRepository.save(product).getProductId();
	}

	@Override
	public Product getProduct(String name) {
		return productRepository.findProductByName(name);
	}
}
