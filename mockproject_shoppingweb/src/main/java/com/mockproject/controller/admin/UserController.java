package com.mockproject.controller.admin;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mockproject.entity.Users;
import com.mockproject.service.UsersService;

@Controller
@RequestMapping("/admin/user")
public class UserController {
	
	@Autowired
	private UsersService usersService;
	
	@GetMapping({"/", ""})
	public String doGetIndex(Model model) {
		List<Users> users = usersService.findAll();
		model.addAttribute("users", users);
		model.addAttribute("userRequest", new Users());
		return "admin/user";
	}
	
	// localhost:8080/admin/user/delete?username={}
	@GetMapping("/delete")
	public String doGetDelete(@RequestParam("username") String username,
			RedirectAttributes redirectAttributes) {
		try {
			usersService.deleteLogical(username);
			redirectAttributes.addFlashAttribute("succeedMessage", "User " + username + " was deleted");
		} catch (Exception ex) {
			ex.printStackTrace();
			redirectAttributes.addFlashAttribute("errorMessage", "Cannot delete user " + username);
		}
		return "redirect:/admin/user";
	}

	// POST: localhost:8080/admin/user/create
	@PostMapping("/create")
	public String doPostCreate(@Valid @ModelAttribute("userRequest") Users userRequest,
			BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		String errorMessage = null;
		try {
			if (bindingResult.hasErrors()) {
				errorMessage = "User is not valid";
				List<FieldError> listError = bindingResult.getFieldErrors();
				listError.forEach(error -> System.out.println(error.getDefaultMessage()));
			} else {
				usersService.save(userRequest);
				redirectAttributes.addFlashAttribute("succeedMessage", "User " + userRequest.getUsername() + " was created");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			errorMessage = "Cannot create new user";
		}
		
		if (!ObjectUtils.isEmpty(errorMessage)) {
			redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
		}
		return "redirect:/admin/user";
	}
	
	@GetMapping("/edit")
	public String doGetEdit(@RequestParam("username") String username, Model model) {
		Users userRequest = usersService.findByUsername(username);
		model.addAttribute("userRequest", userRequest);
		return "admin/user :: #form";
	}
}
