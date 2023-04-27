package com.example.book_selling.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.book_selling.service.ifs.BookSellingService;
import com.example.book_selling.vo.BookSellingRequest;
import com.example.book_selling.vo.BookSellingResponse;

@RestController
public class BookSellingController {
	
	@Autowired
	private BookSellingService bookService;
	
	@PostMapping(value = "add_book")
	public BookSellingResponse AddBook(@RequestBody BookSellingRequest request) {
		return bookService.AddBook(request.getBookList());
	}
	 
	@PostMapping(value = "search_by_category")
	public BookSellingResponse SearchBookByCategory(@RequestBody BookSellingRequest request) {
		return bookService.SearchBookByCategory(request.getStr());
	}
	
	@PostMapping(value = "search_containing")
	public BookSellingResponse SearchBookContaining(@RequestBody BookSellingRequest request) {
		return bookService.SearchBookContaining(request.getIsCustomer(),request.getSearchIsbn(),request.getSearchName(),request.getSearchAuthor());
	}
	
//	@PostMapping(value = "update_book")
//	public BookSellingResponse UpdateBook(@RequestBody BookSellingRequest request) {
//		return bookService.UpdateBook(request.getNewInfo());
//	}

}
