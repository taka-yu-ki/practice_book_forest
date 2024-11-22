package jp.co.feeps.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import jp.co.feeps.constants.DateConstants;
import jp.co.feeps.dto.BookDTO;
import jp.co.feeps.dto.RentalBookDTO;
import jp.co.feeps.dto.UserDTO;
import jp.co.feeps.form.BookSearchForm;
import jp.co.feeps.form.RentalForm;
import jp.co.feeps.service.BookService;
import jp.co.feeps.service.RentalService;

@Controller
@RequestMapping("/books")
public class BookController {
	@Autowired
	private BookService bookService;
	@Autowired
	private RentalService rentalService;

	// 定数を book_list にて共有
	@ModelAttribute
	public void addCommonAttributes(Model model) {
		int rentalDate = DateConstants.RENTAL_DATE;
		model.addAttribute("rentalDate", rentalDate);
	}

	@GetMapping("")
	public String index(Model model) {
		List<BookDTO> books = bookService.getBooks();

		model.addAttribute("books", books);

		return "book_list";
	}

	@GetMapping("/search")
	public String search(BookSearchForm form, Model model) {
		List<BookDTO> books = bookService.searchBooks(form);

		model.addAttribute("books", books);

		return "book_list";
	}

	@GetMapping("/{bookId}/rental")
	public String showRentalBook(@PathVariable int bookId, HttpSession session, Model model,
			RedirectAttributes redirectAttributes) {
		UserDTO user = (UserDTO) session.getAttribute("user");
		int userId = user.getUserId();

		try {
			rentalService.checkRentalBook(userId, bookId);

			RentalBookDTO rentalBook = bookService.getRentalBook(bookId);

			model.addAttribute("rentalBook", rentalBook);

			return "rental";
		} catch (IllegalStateException e) {
			redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());

			return "redirect:/books";
		}

	}

	@PostMapping("/{bookId}/rental")
	public String rentalBook(@PathVariable int bookId, RentalForm form, HttpSession session, Model model) {
		UserDTO user = (UserDTO) session.getAttribute("user");
		int userId = user.getUserId();

		try {
			rentalService.rentalBook(userId, bookId, form);

			return "redirect:/books";
		} catch (ParseException e) {

			model.addAttribute("errorMessage", e.getMessage());

			return "book_list";
		}
	}
}