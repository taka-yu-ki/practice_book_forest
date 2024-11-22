package jp.co.feeps.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "rentals")
@Getter
@Setter
public class Rental {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int rentalId;
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
	private User user;
	@ManyToOne
	@JoinColumn(name = "book_id", referencedColumnName = "book_id", nullable = false)
	private Book book;
	@Column(nullable = false)
	private Date rentalDate;
	@Column(nullable = false)
	private Date dueDate;
}
