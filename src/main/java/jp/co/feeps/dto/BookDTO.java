package jp.co.feeps.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class BookDTO {
	private int bookId;
	private String title;
	private String author;
}
