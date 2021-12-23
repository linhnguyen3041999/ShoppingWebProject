package com.mockproject.service;

import java.sql.SQLException;

import com.mockproject.dto.CartDto;
import com.mockproject.entity.Users;

public interface CartService {

	CartDto updateCart(CartDto cart, Long productId, Integer quantity, boolean isReplace);
	Integer getTotalQuantity(CartDto cart);
	Double getTotalPrice(CartDto cart);
	void insert(CartDto cart, Users user, String address, String phoneNumber) throws SQLException;
}
