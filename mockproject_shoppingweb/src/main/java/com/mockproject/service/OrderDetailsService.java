package com.mockproject.service;

import java.sql.SQLException;

import com.mockproject.dto.CartDetailDto;

public interface OrderDetailsService {

	void insert(CartDetailDto dto) throws SQLException;
}
