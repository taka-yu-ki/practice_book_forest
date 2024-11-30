package jp.co.feeps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import jp.co.feeps.dto.UserDTO;
import jp.co.feeps.dto.UserRegisterDTO;
import jp.co.feeps.form.UserEditForm;
import jp.co.feeps.form.UserRegisterForm;
import jp.co.feeps.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;

	@GetMapping("/register")
	public String showRegisterForm() {

		return "user/account_regist";
	}

	@PostMapping("/register/confirm")
	public String confirmRegister(UserRegisterForm form, Model model) {
		model.addAttribute("form", form);

		return "user/account_regist_confirm";
	}

	@PostMapping("/register/complete")
	public String completeRegister(UserRegisterForm form, Model model) {
		UserRegisterDTO userRegisterDTO = userService.registUser(form);

		model.addAttribute("userId", userRegisterDTO.getUserId());

		return "user/account_regist_complete";
	}

	@GetMapping("/edit")
	public String showEditForm(HttpSession session, Model model) {
		UserDTO user = (UserDTO) session.getAttribute("user");

		model.addAttribute("user", user);

		return "user/account_update";
	}

	@PostMapping("/edit/confirm")
	public String comfirmEdit(UserEditForm form, Model model) {
		model.addAttribute("form", form);

		return "user/account_update_confirm";
	}

	@PostMapping("/edit/update")
	public String updateUser(UserEditForm form, HttpSession session) {
		UserDTO currentUserDTO = (UserDTO) session.getAttribute("user");
		int userId = currentUserDTO.getUserId();

		UserDTO updateUserDTO = (UserDTO) userService.updateUser(userId, form);

		session.setAttribute("user", updateUserDTO);

		return "redirect:/";
	}
}