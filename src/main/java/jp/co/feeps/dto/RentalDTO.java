package jp.co.feeps.dto;

import java.time.LocalDate;

import jp.co.feeps.entity.Book;
import jp.co.feeps.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RentalDTO {
	private int rentalId;
	private User user;
	private Book book;
	private LocalDate rentalLocalDate;
	private LocalDate dueLocalDate;
}