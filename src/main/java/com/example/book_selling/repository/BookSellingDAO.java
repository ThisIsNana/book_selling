package com.example.book_selling.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.book_selling.entity.BookSelling;
import com.example.book_selling.vo.SearchResultConvert;

public interface BookSellingDAO extends JpaRepository<BookSelling, String> {
	//3
	public List<BookSelling> findByCategoryContaining(String str);
	
	//4--使用三個參數搜尋
	public List<BookSelling> findAllByIsbnContainingOrNameContainingOrAuthorContaining(String isbn, String name, String author);

	//4--JPQL模糊搜尋for suppliers ==> 不使用
//	@Transactional
//	@Modifying
//	@Query("SELECT b.isbn, b.name, b.author, b.price, b.inStock, b.soldQuantity FROM BookSelling AS b"
//			+ " WHERE b.isbn LIKE %:keyword% OR b.name LIKE %:keyword% OR b.author LIKE %:keyword%")
//	public List<Object[]> SearchAllByKeywordForSupplier(@Param("keyword") String str);
	
	//5--JPA ==> findAllById即可
	
	//6--銷量排行-
	public List<BookSelling> findTop5ByOrderBySoldQuantityDesc();


	
}
