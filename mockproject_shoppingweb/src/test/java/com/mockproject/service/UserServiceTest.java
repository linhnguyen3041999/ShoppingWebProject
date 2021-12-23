package com.mockproject.service;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.mockproject.entity.Roles;
import com.mockproject.entity.Users;
import com.mockproject.repository.UsersRepo;
import com.mockproject.service.impl.UsersServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
	
	@InjectMocks
	private UsersServiceImpl usersService;
	
	@Mock
	private  UsersRepo repo;
	
	@Mock
	private RolesService RolesService;
	
	@Test
	public void testSave() throws Exception{
		Users expected = new Users(1L , "linhntn", "", "", "", new Timestamp(1L), "", Boolean.FALSE, new Roles());
		Users user = new Users(1L , "linhntn", "", "", "", new Timestamp(1L), "", Boolean.FALSE, new Roles());
		try {
			when(repo.saveAndFlush(expected)).thenReturn(expected);
			Users actual = usersService.save(expected);	
			assertEquals(expected.getUsername(), actual.getUsername());
		} catch (Exception e) {
			e.printStackTrace();
			fail("Test case save user was failed");
		}
	}
}
