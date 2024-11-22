package jp.co.feeps.form;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RentalForm {
	private LocalDate rentalLocalDate;
	private LocalDate dueLocalDate;
}
