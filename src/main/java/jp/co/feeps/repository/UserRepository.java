package jp.co.feeps.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.feeps.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByUserIdAndPassword(@Param("userId") int userId, @Param("password") String password);

	Optional<User> findByUserId(@Param("userId") int userId);
}