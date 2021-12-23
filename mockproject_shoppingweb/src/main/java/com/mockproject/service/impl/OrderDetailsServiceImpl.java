package com.mockproject.service.impl;

import java.sql.SQLException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mockproject.dto.CartDetailDto;
import com.mockproject.repository.OrderDetailsRepo;
import com.mockproject.service.OrderDetailsService;

@Service
public class OrderDetailsServiceImpl implements OrderDetailsService{
	
	@Autowired
	OrderDetailsRepo repo;

	@Override
	@Transactional
	public void insert(CartDetailDto dto) throws SQLException {
		repo.insert(dto);
	}
}
