package jp.co.feeps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import jp.co.feeps.dto.UserDTO;
import jp.co.feeps.dto.UserRegisterDTO;
import jp.co.feeps.form.UserLoginForm;
import jp.co.feeps.form.UserRegisterForm;
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
			UserDTO userDTO = userService.loginCheck(form);

			session.setAttribute("user", userDTO);

			return "redirect:/";
		} catch (RuntimeException e) {
			model.addAttribute("errorMessage", e.getMessage());

			return "/index";
		}
	}

	@GetMapping("/register")
	public String register() {

		return "/account_regist";
	}

	@PostMapping("/register/confirm")
	public String registUser(UserRegisterForm form, HttpSession session) {
		session.setAttribute("form", form);

		return "/account_regist_confirm";
	}

	@PostMapping("/register/complete")
	public String confirmUser(UserRegisterForm form, Model model, HttpSession session) {
		UserRegisterDTO userRegisterDTO = userService.registUser(form);

		session.removeAttribute("userName");
		session.removeAttribute("password");
		session.removeAttribute("email");

		model.addAttribute("userId", userRegisterDTO.getUserId());

		return "/account_regist_complete";
	}
}