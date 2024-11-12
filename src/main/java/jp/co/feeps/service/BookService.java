package jp.co.feeps.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.feeps.dto.BookDTO;
import jp.co.feeps.entity.Book;
import jp.co.feeps.form.BookSearchForm;
import jp.co.feeps.repository.BookRepository;

@Service
public class BookService {
	@Autowired
	private BookRepository bookRepository;

	public List<BookDTO> getBooks() {
		List<Book> books = bookRepository.findAll();

		List<BookDTO> bookDTOs = books.stream()
				.map(book -> new BookDTO(book.getBookId(), book.getTitle(), book.getAuthor()))
				.collect(Collectors.toList());

		return bookDTOs;
	}

	public List<BookDTO> findBooksByTitle(BookSearchForm form) {
		String title = form.getTitle();
		List<Book> books;

		if (title != null && !title.trim().isEmpty()) {
			books = bookRepository.findByTitleContaining(title);
		} else {
			books = bookRepository.findAll();
		}

		List<BookDTO> bookDTOs = books.stream()
				.map(book -> new BookDTO(book.getBookId(), book.getTitle(), book.getAuthor()))
				.collect(Collectors.toList());

		return bookDTOs;
	}
}