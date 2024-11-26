package jp.co.feeps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.feeps.dto.RentalBookDTO;
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
}
