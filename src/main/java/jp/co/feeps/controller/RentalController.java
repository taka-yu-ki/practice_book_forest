package jp.co.feeps.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import jp.co.feeps.dto.RentalBookDTO;
import jp.co.feeps.dto.RentalHistoryDTO;
import jp.co.feeps.dto.UserDTO;
import jp.co.feeps.form.RentalDetailForm;
import jp.co.feeps.service.RentalService;

@Controller
@RequestMapping("/rentals")
public class RentalController {
	@Autowired
	RentalService rentalService;

	@GetMapping("")
	public String showRentalBook(RentalDetailForm form, Model model) {
		RentalBookDTO rentalBook = rentalService.convertFormToDTO(form);

		model.addAttribute("rentalBook", rentalBook);

		return "current_rental_book";
	}

	@GetMapping("/history")
	public String showRentalHistories(HttpSession session, Model model) {
		UserDTO user = (UserDTO) session.getAttribute("user");
		int userId = user.getUserId();

		List<RentalHistoryDTO> rentalHistories = rentalService.getRentalHistories(userId);

		model.addAttribute("rentalHistories", rentalHistories);

		return "history";
	}
}
