package jp.co.feeps.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.feeps.dto.BookDTO;
import jp.co.feeps.form.BookSearchForm;
import jp.co.feeps.service.BookService;

@Controller
@RequestMapping("/books")
public class BookController {
	@Autowired
	private BookService bookService;

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
}
