package jp.co.feeps.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.feeps.entity.Book;
import jp.co.feeps.entity.Rental;
import jp.co.feeps.entity.User;
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
}
