package jp.co.feeps.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "books")
@Getter
@Setter
public class Book {
	@Id
	private int bookId;
	@Column(nullable = false)
	private String title;
	@Column(nullable = false)
	private String author;
}
