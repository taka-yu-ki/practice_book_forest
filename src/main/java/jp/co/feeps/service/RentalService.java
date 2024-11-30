package jp.co.feeps.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.feeps.dto.RentalDetailDTO;
import jp.co.feeps.dto.RentalHistoryDTO;
import jp.co.feeps.dto.RentalInfoDTO;
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
		Date today = new Date();
		boolean isRental = rentalRepository.existsActiveRentalByUserIdAndBookId(userId, bookId, today);

		// 貸出期間中であればエラーを返す
		if (isRental) {
			throw new IllegalStateException("既に貸出中です。");
		}
	}

	public void rentalBook(int userId, int bookId, RentalForm form) throws ParseException {
		LocalDate rentalLocalDate = form.getRentalLocalDate();
		LocalDate dueLocalDate = form.getDueLocalDate();

		// プロキシを取得
		User user = userRepository.getReferenceById(userId);
		Book book = bookRepository.getReferenceById(bookId);

		// 保存型は Date であるため、LocalDate を変換する
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

	public List<RentalInfoDTO> getRentalInfos(int userId) {
		Date today = new Date();
		List<Rental> rentals = rentalRepository.findBetweenRentalDateAndDueDate(userId, today);

		List<RentalInfoDTO> rentalInfoDTOs = rentals.stream().map(rental -> {
			RentalInfoDTO rentalInfoDTO = new RentalInfoDTO();
			rentalInfoDTO.setRentalId(rental.getRentalId());
			rentalInfoDTO.setTitle(rental.getBook().getTitle());

			return rentalInfoDTO;
		}).collect(Collectors.toList());

		return rentalInfoDTOs;
	}

	public RentalDetailDTO getRentalDetail(int rentalId) {
		Optional<Rental> rentalOpt = rentalRepository.findByRentalId(rentalId);
		Rental rental = rentalOpt.orElseThrow(() -> new RuntimeException("レンタル詳細が見つかりません"));

		// html では LocalDate として扱うため、Date を変換する
		LocalDate rentalLocalDate = rental.getRentalDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate dueLocalDate = rental.getDueDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		RentalDetailDTO rentalDetailDTO = new RentalDetailDTO();
		rentalDetailDTO.setBookId(rental.getBook().getBookId());
		rentalDetailDTO.setTitle(rental.getBook().getTitle());
		rentalDetailDTO.setAuthor(rental.getBook().getAuthor());
		rentalDetailDTO.setRentalLocalDate(rentalLocalDate);
		rentalDetailDTO.setDueLocalDate(dueLocalDate);

		return rentalDetailDTO;
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