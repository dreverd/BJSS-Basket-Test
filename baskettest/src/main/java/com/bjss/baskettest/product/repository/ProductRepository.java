package com.bjss.baskettest.product.repository;

import java.util.List;

import org.springframework.data.repository.Repository;

import com.bjss.baskettest.product.model.Product;

public interface ProductRepository extends Repository<Product, Long> {
	List<Product> findAll();
}
