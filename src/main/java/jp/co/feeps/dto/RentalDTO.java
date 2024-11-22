package jp.co.feeps.dto;

import java.util.Date;

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
	private Date rentalDate;
	private Date dueDate;
}