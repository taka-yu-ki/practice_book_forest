package jp.co.feeps.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RentalDetailDTO {
	private int bookId;
	private String title;
	private String author;
	private LocalDate rentalLocalDate;
	private LocalDate dueLocalDate;
}