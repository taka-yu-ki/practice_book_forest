package jp.co.feeps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import jp.co.feeps.dto.UserDTO;
import jp.co.feeps.form.UserLoginForm;
import jp.co.feeps.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;

	@GetMapping("/login")
	public String login() {
		return "/index";
	}

	@PostMapping("/login")
	public String loginUser(UserLoginForm form, HttpSession session, Model model) {
		try {
			UserDTO user = userService.loginCheck(form);

			session.setAttribute("user", user);

			return "/book_list";
		} catch (RuntimeException e) {
			model.addAttribute("errorMessage", e.getMessage());

			return "/index";
		}
	}

	@GetMapping("/register")
	public String register() {
		return "/account_regist";
	}
}