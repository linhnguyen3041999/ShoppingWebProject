package com.mockproject.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.mockproject.constant.RolesConst;
import com.mockproject.entity.Users;
import com.mockproject.repository.UsersRepo;
import com.mockproject.service.RolesService;
import com.mockproject.service.UsersService;

@Service
public class UsersServiceImpl implements UsersService {
	
	private BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
	
	@Autowired
	private UsersRepo repo;
	
	@Autowired
	private RolesService rolesService;

	@Override
	public Users findById(Long id) {
		Optional<Users> optional = repo.findById(id);
		return optional.isPresent() ? optional.get() : null;
	}

	@Override
	public Users doLogin(String username, String password) {
		Users user = repo.findByUsername(username);
		if (ObjectUtils.isEmpty(user)) {
			return null;
		} else {
			// match password
			boolean checkPassword = bcrypt.matches(password, user.getHashPassword());
			return checkPassword == true ? user : null;
		}
	}

	@Override
	public List<Users> findAll() {
		return repo.findByIsDeleted(Boolean.FALSE);
	}

	@Transactional
	@Override
	public Users save(Users user) {
		user.setHashPassword(bcrypt.encode(user.getHashPassword()));
		user.setIsDeleted(Boolean.FALSE);
		user.setRole(rolesService.findByDescription(RolesConst.ROLE_USER));
		return repo.saveAndFlush(user);
	}

	@Transactional
	@Override
	public void update(Users user) throws Exception {
		if (ObjectUtils.isEmpty(user)) {
			throw new Exception("User cannot be empty");
		}
		
		if (user.getHashPassword().length() == 0) {
			repo.updateNonPass(user.getFullname(), user.getEmail(), user.getUsername());
		} else {
			user.setHashPassword(bcrypt.encode(user.getHashPassword()));
			repo.update(user.getFullname(), user.getEmail(), user.getHashPassword(), user.getUsername());
		}
	}

	@Transactional
	@Override
	public void deleteLogical(String username) {
		repo.deleteLogical(username);
	}

	@Override
	public Users findByUsername(String username) {
		return repo.findByUsername(username);
	}
}
