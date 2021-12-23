package com.mockproject.util;

import javax.servlet.http.HttpSession;

import com.mockproject.dto.CartDto;

public class SessionUtils {

	public static void validateCart(HttpSession session) {
		CartDto currentCart = (CartDto) session.getAttribute("currentCart");
		if (currentCart == null) {
			session.setAttribute("currentCart", new CartDto());
		}
	}
	
	public static CartDto getCurrentCart(HttpSession session) {
		return (CartDto) session.getAttribute("currentCart");
	}
}
