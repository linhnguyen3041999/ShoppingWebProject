package com.mockproject.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mockproject.constant.SessionConst;
import com.mockproject.entity.Products;
import com.mockproject.entity.Users;
import com.mockproject.service.ProductsService;
import com.mockproject.service.UsersService;

@Controller
@RequestMapping("/")
public class HomeController {
	
	@Autowired
	private UsersService usersService;
	
	@Autowired
	private ProductsService productsService;

	// localhost:8080/index
	@GetMapping("index")
	public String doGetIndex(Model model) {
		List<Products> products = productsService.findAll();
		model.addAttribute("products", products);
		return "user/index";
	}
	
	// GET: localhost:8080/login
	@GetMapping("login")
	public String doGetLogin(Model model) {
		model.addAttribute("userRequest", new Users());
		return "user/login";
	}
	
	// GET: localhost:8080/logout
	@GetMapping("logout")
	public String doGetLogout(HttpSession session) {
		session.removeAttribute(SessionConst.CURRENT_USER);
		return "redirect:/index";
	}
	
	// GET: localhost:8080/register
	@GetMapping("register")
	public String doGetRegister(Model model) {
		return "user/register";
	}
	
	// POST: localhost:8080/login
	@PostMapping("login")
	public String doPostLogin(@ModelAttribute("userRequest") Users userRequest,
			Model model, HttpSession session) {
		Users userResponse = usersService.doLogin(userRequest.getUsername(), userRequest.getHashPassword());
		if (ObjectUtils.isEmpty(userResponse)) {
			String message = "Login failed, please try again";
			model.addAttribute("message", message);
			return "user/login";
		} else {
			session.setAttribute(SessionConst.CURRENT_USER, userResponse);
			return "redirect:/index";
		}
 	}
	
	@GetMapping("test")
	public String doGetTest() {
		return "admin/index";
	}
	
	@GetMapping("test2")
	public String doGetTest2() {
		return "admin/user";
	}
}
