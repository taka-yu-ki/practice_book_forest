package jp.co.feeps.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.feeps.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

	List<Book> findByTitleContaining(String title);

}
