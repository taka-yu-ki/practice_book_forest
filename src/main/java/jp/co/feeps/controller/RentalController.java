package jp.co.feeps.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import jp.co.feeps.dto.RentalDetailDTO;
import jp.co.feeps.dto.RentalHistoryDTO;
import jp.co.feeps.dto.UserDTO;
import jp.co.feeps.service.RentalService;

@Controller
@RequestMapping("/rentals")
public class RentalController {
	@Autowired
	RentalService rentalService;

	@GetMapping("/{rentalId}")
	public String showRentalBook(@PathVariable int rentalId, Model model) {
		RentalDetailDTO rentalDetail = rentalService.getRentalDetail(rentalId);

		model.addAttribute("rentalDetail", rentalDetail);

		return "rental/current_rental_book";
	}

	@GetMapping("/history")
	public String showRentalHistories(HttpSession session, Model model) {
		UserDTO user = (UserDTO) session.getAttribute("user");
		int userId = user.getUserId();

		List<RentalHistoryDTO> rentalHistories = rentalService.getRentalHistories(userId);

		model.addAttribute("rentalHistories", rentalHistories);

		return "rental/history";
	}
}
