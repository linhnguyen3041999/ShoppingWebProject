package com.mockproject.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.mockproject.entity.ProductTypes;
import com.mockproject.entity.Products;
import com.mockproject.entity.UnitTypes;
import com.mockproject.repository.ProductsRepo;
import com.mockproject.service.impl.ProductsServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {
	
	@InjectMocks
	private ProductsServiceImpl productsService;
	
	@Mock
	private ProductsRepo repo;
	
	@Test
	public void testFindById() {
		Optional<Products>  expected = Optional.of(new Products(1L, "mock Product", 1, 123D, "", "" ,"", Boolean.FALSE, new ProductTypes(), new UnitTypes()));
		Optional<Products>  expected1 = Optional.of(new Products());
		when(repo.findById(1L)).thenReturn(expected);
		when(repo.findById(2L)).thenReturn(expected1);
		Products actual = productsService.findById(2L);
		assertEquals(expected.get().getId(), actual.getId());
	}
}
