package jp.co.feeps.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.feeps.dto.RentalBookDTO;
import jp.co.feeps.dto.RentalDTO;
import jp.co.feeps.dto.RentalHistoryDTO;
import jp.co.feeps.entity.Book;
import jp.co.feeps.entity.Rental;
import jp.co.feeps.entity.User;
import jp.co.feeps.form.RentalDetailForm;
import jp.co.feeps.form.RentalForm;
import jp.co.feeps.repository.BookRepository;
import jp.co.feeps.repository.RentalRepository;
import jp.co.feeps.repository.UserRepository;

@Service
public class RentalService {
	@Autowired
	private RentalRepository rentalRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BookRepository bookRepository;

	public void checkRentalBook(int userId, int bookId) throws IllegalStateException {
		boolean isRental = rentalRepository.existsByUserUserIdAndBookBookId(userId, bookId);

		if (isRental) {
			throw new IllegalStateException("既に貸出中です。");
		}
	}

	public void rentalBook(int userId, int bookId, RentalForm form) throws ParseException {
		LocalDate rentalLocalDate = form.getRentalLocalDate();
		LocalDate dueLocalDate = form.getDueLocalDate();

		User user = userRepository.getReferenceById(userId);
		Book book = bookRepository.getReferenceById(bookId);

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date rentalDate = (Date) simpleDateFormat.parse(rentalLocalDate.toString());
		Date dueDate = (Date) simpleDateFormat.parse(dueLocalDate.toString());

		Rental rental = new Rental();
		rental.setUser(user);
		rental.setBook(book);
		rental.setRentalDate(rentalDate);
		rental.setDueDate(dueDate);

		rentalRepository.save(rental);
	}

	public List<RentalDTO> getRentalBooks(int userId) {
		Date today = new Date();
		List<Rental> rentals = rentalRepository.findBetweenRentalDateAndDueDate(userId, today);

		List<RentalDTO> rentalBookDTOs = rentals.stream().map(rental -> {
			LocalDate rentalLocalDate = rental.getRentalDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate dueLocalDate = rental.getDueDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

			RentalDTO rentalBookDTO = new RentalDTO();
			rentalBookDTO.setRentalId(rental.getRentalId());
			rentalBookDTO.setUser(rental.getUser());
			rentalBookDTO.setBook(rental.getBook());
			rentalBookDTO.setRentalLocalDate(rentalLocalDate);
			rentalBookDTO.setDueLocalDate(dueLocalDate);

			return rentalBookDTO;
		}).collect(Collectors.toList());

		return rentalBookDTOs;
	}

	public RentalBookDTO convertFormToDTO(RentalDetailForm form) {
		int bookId = form.getBookId();
		String title = form.getTitle();
		String author = form.getAuthor();
		LocalDate rentalLocalDate = form.getRentalLocalDate();
		LocalDate dueLocalDate = form.getDueLocalDate();

		RentalBookDTO rentalBook = new RentalBookDTO();
		rentalBook.setBookId(bookId);
		rentalBook.setTitle(title);
		rentalBook.setAuthor(author);
		rentalBook.setRentalLocalDate(rentalLocalDate);
		rentalBook.setDueLocalDate(dueLocalDate);

		return rentalBook;
	}

	public List<RentalHistoryDTO> getRentalHistories(int userId) {
		List<Rental> rentals = rentalRepository.findByUserUserId(userId);

		List<RentalHistoryDTO> rentalHistoryDTOs = rentals.stream().map(rental -> {
			LocalDate rentalLocalDate = rental.getRentalDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

			RentalHistoryDTO rentalHistoryDTO = new RentalHistoryDTO();
			rentalHistoryDTO.setTitle(rental.getBook().getTitle());
			rentalHistoryDTO.setAuthor(rental.getBook().getAuthor());
			rentalHistoryDTO.setRentalLocalDate(rentalLocalDate);

			return rentalHistoryDTO;
		}).collect(Collectors.toList());

		return rentalHistoryDTOs;
	}
}