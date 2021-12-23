package com.mockproject.service;

import java.sql.SQLException;

import com.mockproject.entity.Orders;

public interface OrdersService {

	Orders insert(Orders order) throws SQLException;
}
