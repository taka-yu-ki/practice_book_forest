package jp.co.feeps.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jp.co.feeps.entity.Rental;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Integer> {
	boolean existsByUserUserIdAndBookBookId(int userId, int bookId);

	@Query("SELECT r FROM Rental r WHERE r.user.userId = :userId AND :today BETWEEN r.rentalDate AND r.dueDate")
	List<Rental> findBetweenRentalDateAndDueDate(int userId, Date today);

	List<Rental> findByUserUserId(int userId);
}
