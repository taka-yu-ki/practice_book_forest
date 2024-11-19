package jp.co.feeps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import jp.co.feeps.dto.UserDTO;
import jp.co.feeps.form.UserLoginForm;
import jp.co.feeps.service.AuthService;

@Controller
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private AuthService authService;

	@GetMapping("/login")
	public String showLoginForm() {
		return "index";
	}

	@PostMapping("/login")
	public String loginUser(UserLoginForm form, HttpSession session, Model model) {
		try {
			UserDTO userDTO = authService.loginCheck(form);

			session.setAttribute("user", userDTO);

			return "redirect:/";
		} catch (RuntimeException e) {
			model.addAttribute("errorMessage", e.getMessage());

			return "index";
		}
	}

	@GetMapping("/logout")
	public String logoutUser(HttpSession session) {
		session.removeAttribute("user");

		return "redirect:/auth/login";
	}
}
