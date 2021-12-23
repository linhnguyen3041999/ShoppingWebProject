package com.mockproject.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mockproject.entity.ProductTypes;
import com.mockproject.repository.ProductTypesRepo;
import com.mockproject.service.ProductTypesService;

@Service
public class ProductTypesServiceImpl implements ProductTypesService {

	@Autowired
	private ProductTypesRepo repo;

	@Override
	public List<ProductTypes> findAll() {
		return repo.findByIsDeleted(Boolean.TRUE);
	}
}
