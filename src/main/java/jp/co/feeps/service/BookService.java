package jp.co.feeps.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.feeps.constants.DateConstants;
import jp.co.feeps.dto.BookDTO;
import jp.co.feeps.dto.RentalRequestDTO;
import jp.co.feeps.entity.Book;
import jp.co.feeps.form.BookSearchForm;
import jp.co.feeps.repository.BookRepository;

@Service
public class BookService {
	@Autowired
	private BookRepository bookRepository;

	public List<BookDTO> getBooks() {
		List<Book> books = bookRepository.findAll();

		List<BookDTO> bookDTOs = books.stream().map(book -> {
			BookDTO bookDTO = new BookDTO();
			bookDTO.setBookId(book.getBookId());
			bookDTO.setTitle(book.getTitle());
			bookDTO.setAuthor(book.getAuthor());

			return bookDTO;
		}).collect(Collectors.toList());

		return bookDTOs;
	}

	public List<BookDTO> searchBooks(BookSearchForm form) {
		List<Book> books;

		String title = form.getTitle();

		// 検索文字列前後の空白を削除後、空文字であれば全権取得を行う
		if (title != null && !title.trim().isEmpty()) {
			books = bookRepository.findByTitleContaining(title);
		} else {
			books = bookRepository.findAll();
		}

		if (books.isEmpty()) {
			throw new RuntimeException("お探しの書籍は見つかりませんでした。");
		}

		List<BookDTO> bookDTOs = books.stream().map(book -> {
			BookDTO bookDTO = new BookDTO();
			bookDTO.setBookId(book.getBookId());
			bookDTO.setTitle(book.getTitle());
			bookDTO.setAuthor(book.getAuthor());

			return bookDTO;
		}).collect(Collectors.toList());

		return bookDTOs;
	}

	public RentalRequestDTO getRentalRequest(int bookId) {
		Optional<Book> bookOpt = bookRepository.findById(bookId);
		Book book = bookOpt.orElseThrow(() -> new RuntimeException("書籍が見つかりません"));

		int rentalDate = DateConstants.RENTAL_DATE;

		// 本日の日付と5日後(本日含め)の日付をここで生成する
		LocalDate rentalLocalDate = LocalDate.now();
		LocalDate dueLocalDate = rentalLocalDate.plusDays(rentalDate - 1);

		RentalRequestDTO rentalRequestDTO = new RentalRequestDTO();
		rentalRequestDTO.setBookId(book.getBookId());
		rentalRequestDTO.setTitle(book.getTitle());
		rentalRequestDTO.setAuthor(book.getAuthor());
		rentalRequestDTO.setRentalLocalDate(rentalLocalDate);
		rentalRequestDTO.setDueLocalDate(dueLocalDate);

		return rentalRequestDTO;
	}
}