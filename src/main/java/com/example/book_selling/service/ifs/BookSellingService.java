package com.example.book_selling.service.ifs;

import java.util.List;
import java.util.Map;

import com.example.book_selling.entity.BookSelling;
import com.example.book_selling.vo.BookSellingResponse;

public interface BookSellingService {
	//2
	public BookSellingResponse addBook(List<BookSelling> bookList);
	//3
	public BookSellingResponse searchBookByCategory(String str);
	//4
	public BookSellingResponse searchBookContaining(Boolean isCustomer, String str);
//	public BookSellingResponse SearchBookContaining(Boolean isCustomer, String isbn, String name, String author);
	//5
	public BookSellingResponse updateBook(Map<String, Integer> stockUpdate);
	public BookSellingResponse updateBook(String isbn, int price);
	public BookSellingResponse updateBook(String isbn, String category);
//	public BookSellingResponse updateBook(BookSelling book););
	
	//6
	public BookSellingResponse orderBook(Map<String,Integer> map);
	
	public BookSellingResponse saleRank();

	
}
