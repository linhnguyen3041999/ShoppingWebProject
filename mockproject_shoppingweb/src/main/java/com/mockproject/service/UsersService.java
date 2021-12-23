package com.mockproject.service;

import java.util.List;

import com.mockproject.entity.Users;

public interface UsersService {
	Users findByUsername(String username);
	Users findById(Long id);
	Users doLogin(String username, String password);
	List<Users> findAll();
	Users save(Users user);
	void update(Users user) throws Exception;
	void deleteLogical(String username);
}
