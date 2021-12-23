package com.mockproject.service.impl;

import java.sql.SQLException;
import java.util.HashMap;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mockproject.dto.CartDetailDto;
import com.mockproject.dto.CartDto;
import com.mockproject.entity.Orders;
import com.mockproject.entity.Products;
import com.mockproject.entity.Users;
import com.mockproject.service.CartService;
import com.mockproject.service.OrderDetailsService;
import com.mockproject.service.OrdersService;
import com.mockproject.service.ProductsService;

@Service
public class CartServiceImpl implements CartService {
	
	@Autowired
	private ProductsService productsService;
	
	@Autowired
	private OrdersService ordersService;
	
	@Autowired
	private OrderDetailsService orderDetailsService;

	@Override
	public CartDto updateCart(CartDto cart, Long productId, Integer quantity, boolean isReplace) {
		Products product = productsService.findById(productId);
		HashMap<Long, CartDetailDto> listDetail = cart.getListDetail();
		
		// 1- them moi
		// 2- update: 2a- cong don; 2b- replace
		// 3- remove
		if (!listDetail.containsKey(productId)) {
			CartDetailDto cartDetail = new CartDetailDto();
			cartDetail.setProductId(productId);
			cartDetail.setName(product.getName());
			cartDetail.setPrice(product.getPrice());
			cartDetail.setQuantity(quantity);
			cartDetail.setImgUrl(product.getImgUrl());
			cartDetail.setSlug(product.getSlug());
			listDetail.put(productId, cartDetail);
		} else if (quantity > 0) {
			if (isReplace == true) {
				listDetail.get(productId).setQuantity(quantity);
			} else {
				Integer oldQuantity = listDetail.get(productId).getQuantity();
				listDetail.get(productId).setQuantity(oldQuantity + quantity);
			}
		} else {
			listDetail.remove(productId);
		}
		cart.setTotalQuantity(getTotalQuantity(cart));
		cart.setTotalPrice(getTotalPrice(cart));
		return cart;
	}

	@Override
	public Integer getTotalQuantity(CartDto cart) {
		HashMap<Long, CartDetailDto> listDetail = cart.getListDetail();
		Integer totalQuantity = 0;
		for (CartDetailDto cartDetail : listDetail.values()) {
			totalQuantity += cartDetail.getQuantity();
		}
		return totalQuantity;
	}

	@Override
	public Double getTotalPrice(CartDto cart) {
		HashMap<Long, CartDetailDto> listDetail = cart.getListDetail();
		Double totalPrice = 0D;
		for (CartDetailDto cartDetail : listDetail.values()) {
			totalPrice += cartDetail.getQuantity() * cartDetail.getPrice();
		}
		return totalPrice;
	}

	@Transactional
	@Override
	public void insert(CartDto cart, Users user, String address, String phoneNumber) throws SQLException {
		Orders order = new Orders();
		order.setAddress(address);
		order.setPhone(phoneNumber);
		order.setUser(user);
		try {
			Orders orderReturn = ordersService.insert(order);
			for (CartDetailDto cartDetailDto : cart.getListDetail().values()) {
				cartDetailDto.setOrderId(orderReturn.getId());
				orderDetailsService.insert(cartDetailDto);
				
				// update quantity
				Products product = productsService.findById(cartDetailDto.getProductId());
				Integer newQuantity = product.getQuantity() - cartDetailDto.getQuantity();
				productsService.updateQuantity(newQuantity, product.getId());
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new SQLException("Cannot insert to DB");
		}
	}
}
