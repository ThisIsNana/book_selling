package com.example.book_selling.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.book_selling.entity.BookSelling;
import com.example.book_selling.vo.SearchForCustomer;

public interface BookSellingDAO extends JpaRepository<BookSelling, String> {

	public List<BookSelling> findByCategoryContaining(String str);
	
	public List<BookSelling> findAllByIsbnContainingOrNameContainingOrAuthorContaining(String isbn, String name, String author);
//	public List<BookSelling> findAllByIsbnOrNameOrAuthorContaining(String str);
	
	@Transactional
	@Modifying
	@Query("SELECT b FROM BookSelling AS b WHERE b.isbn LIKE %:keyword% OR b.name LIKE %:keyword% OR b.author LIKE %:keyword%")
	public List<BookSelling> findAllByKeyword(@Param("keyword") String str);
	
	@Transactional
	@Modifying
	@Query("SELECT b.isbn, b.name, b.author, b.price FROM BookSelling AS b"
			+ " WHERE b.isbn LIKE %:keyword% OR b.name LIKE %:keyword% OR b.author LIKE %:keyword%")
	public List<Object[]> SearchAllByKeywordForCustomer(@Param("keyword") String str);
	
	
//	@Transactional
//	@Modifying
//	@Query("SELECT b.isbn, b.name, b.author, b.price FROM BookSelling AS b"
//			+ " WHERE b.isbn LIKE %:keyword% OR b.name LIKE %:keyword% OR b.author LIKE %:keyword%")
//	public List<SearchForCustomer> SearchAllByKeywordForCustomer(@Param("keyword") String str);
	
	@Transactional
	@Modifying
	@Query("SELECT b.isbn, b.name, b.author, b.price, b.inStock, b.soldQuantity FROM BookSelling AS b"
			+ " WHERE b.isbn LIKE %:keyword% OR b.name LIKE %:keyword% OR b.author LIKE %:keyword%")
	public List<Object[]> SearchAllByKeywordForSupplier(@Param("keyword") String str);

	@Transactional
	@Modifying
	@Query("SELECT b.isbn, b.name, b.author, b.price FROM BookSelling AS b WHERE b.isbn IN :isbnList")
	public List<Object[]> SearchByOrder(@Param("isbn") List<String> inputIsbnList);
	
	
	
//	@Transactional
//	@Modifying
//	@Query("update BookSelling b set "
//			+ "b.price = :newPrice, "
//			+ "b.category = :newCategory, "
//			+ "b.in_stock = :newInStock where b.isbn = :oldIsbn")
//	public int updateBookByIsbn(
//			@Param("newPrice") int inputPrice, 
//			@Param("newCategory") String inputCategory, 
//			@Param("newInStock") int inputInStock, 
//			@Param("oldIsbn") String inputIsbn);
	
//	public List<BookSelling> findByNameContaining(String name);
//
//	public List<BookSelling> findByAuthorContaining(String author);
//	
//	public List<BookSelling> findByIsbnContaining(String isbn);
	
}
