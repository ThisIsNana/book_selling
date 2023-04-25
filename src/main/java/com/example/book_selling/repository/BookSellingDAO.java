package com.example.book_selling.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.book_selling.entity.BookSelling;

public interface BookSellingDAO extends JpaRepository<BookSelling, String> {

	public List<BookSelling> findByCategoryContaining(String str);
	
	public List<BookSelling> findByIdContainingOrNameContainingOrAuthorContaining(String str);
	
	
}
