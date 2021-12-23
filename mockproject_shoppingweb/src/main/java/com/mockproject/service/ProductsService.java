package com.mockproject.service;

import java.util.List;

import com.mockproject.entity.Products;

public interface ProductsService {

	List<Products> findAll();
	List<Products> findBySlug(String slug);
	Products findById(Long id);
	void updateQuantity(Integer quantity, Long productId);
}
