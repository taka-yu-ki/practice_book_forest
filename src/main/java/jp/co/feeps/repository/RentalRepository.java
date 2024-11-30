package jp.co.feeps.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.feeps.entity.Rental;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Integer> {
	@Query("SELECT EXISTS (SELECT r FROM Rental r WHERE r.user.userId = :userId AND r.book.bookId = :bookId AND :today BETWEEN r.rentalDate AND r.dueDate)")
	boolean existsActiveRentalByUserIdAndBookId(@Param("userId") int userId, @Param("bookId") int bookId, Date today);

	@Query("SELECT r FROM Rental r WHERE r.user.userId = :userId AND :today BETWEEN r.rentalDate AND r.dueDate ORDER BY r.dueDate ASC")
	List<Rental> findBetweenRentalDateAndDueDate(@Param("userId") int userId, Date today);

	List<Rental> findByUserUserId(@Param("userId") int userId);
}
