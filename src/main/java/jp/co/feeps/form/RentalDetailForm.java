package jp.co.feeps.form;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RentalDetailForm {
	int bookId;
	String title;
	String author;
	LocalDate rentalLocalDate;
	LocalDate dueLocalDate;
}
