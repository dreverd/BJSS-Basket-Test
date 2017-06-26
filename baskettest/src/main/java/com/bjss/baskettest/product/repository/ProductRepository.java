package com.bjss.baskettest.product.repository;

import java.util.List;

import org.springframework.data.repository.Repository;

import com.bjss.baskettest.product.model.Product;

/**
 * Spring data repository, spring generates implementing class.
 */
public interface ProductRepository extends Repository<Product, Long> {
	List<Product> findAll();

	Product findProductByName(String name);

	Product save(Product product);
}
