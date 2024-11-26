package jp.co.feeps.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.feeps.entity.Rental;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Integer> {
	boolean existsByUserUserIdAndBookBookId(int userId, int bookId);

	List<Rental> findByUserUserId(int userId);
}
