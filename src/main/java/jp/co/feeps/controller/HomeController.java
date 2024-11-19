package jp.co.feeps.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import jp.co.feeps.dto.UserDTO;

@Controller
@RequestMapping("/")
public class HomeController {
	@GetMapping("")
	public String home(HttpSession session) {
		UserDTO user = (UserDTO) session.getAttribute("user");

		if (user == null) {
			return "redirect:/auth/login";
		}

		return "redirect:/books";
	}
}