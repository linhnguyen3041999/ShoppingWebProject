package com.mockproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mockproject.entity.Products;
import com.mockproject.service.ProductsService;

@Controller
@RequestMapping("/san-pham")
public class ProductController {
	
	@Autowired
	private ProductsService productsService;
	
	// GET: localhost:8080/san-pham/{slug-type}
	@GetMapping("/{slug-type}")
	public String doGetBySlug(@PathVariable("slug-type") String slugType, Model model) {
		List<Products> products = productsService.findBySlug(slugType);
		model.addAttribute("products", products);
		if (!products.isEmpty()) {
			model.addAttribute("productType", products.get(0).getProductType());
		}
		return "user/product-type";
	}

}
