package com.example.book_selling.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.book_selling.entity.BookSelling;

public interface BookSellingDAO extends JpaRepository<BookSelling, String> {

	public List<BookSelling> findByCategoryContaining(String str);
	
	public List<BookSelling> findAllByIsbnContainingOrNameContainingOrAuthorContaining(String isbn, String name, String author);
	
	@Transactional
	@Modifying
	@Query("update book b set "
			+ "b.price = :newPrice, "
			+ "b.category = :newCategory, "
			+ "b.in_stock = :newInStock where b.isbn = :oldIsbn")
	public int updateBookByIsbn(
			@Param("newPrice") int inputPrice, 
			@Param("newCategory") String inputCategory, 
			@Param("newInStock") int inputInStock, 
			@Param("oldIsbn") String inputIsbn);
	
//	public List<BookSelling> findByNameContaining(String name);
//
//	public List<BookSelling> findByAuthorContaining(String author);
//	
//	public List<BookSelling> findByIsbnContaining(String isbn);
	
}
