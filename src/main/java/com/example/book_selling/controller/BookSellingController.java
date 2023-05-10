package com.example.book_selling.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
		return bookService.addBook(request.getBookList());
	}
	 
	@PostMapping(value = "search_by_category")
	public BookSellingResponse SearchBookByCategory(@RequestBody BookSellingRequest request) {
		return bookService.searchBookByCategory(request.getStr());
	}
	
	@PostMapping(value = "search_containing") //JPQL
	public BookSellingResponse SearchBookContaining(@RequestBody BookSellingRequest request) {
		return bookService.searchBookContaining(request.getIsCustomer(),request.getStr());
	}
//	@PostMapping(value = "search_containing") //JPA
//	public BookSellingResponse SearchBookContaining(@RequestBody BookSellingRequest request) {
//		return bookService.SearchBookContaining(request.getIsCustomer(),request.getSearchIsbn(),request.getSearchName(),request.getSearchAuthor());
//	}
	
//	@PostMapping(value = "update_book")
//	public BookSellingResponse UpdateBook(@RequestBody BookSellingRequest request) {
//		return bookService.updateBook(request.getSearchIsbn(),request.getUpdatePrice(),request.getUpdateCategory(),request.getUpdateInStock());
//	}
	
	@PostMapping(value = "order")
	public BookSellingResponse OrderBook(@RequestBody BookSellingRequest request) {
		return bookService.orderBook(request.getOrderMap());
	}
	
	@GetMapping(value = "show_rank")
	public BookSellingResponse SaleRank() {
		return bookService.saleRank();
	}

}
