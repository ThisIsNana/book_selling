package com.example.book_selling.service.ifs;

import java.util.List;
import java.util.Map;

import com.example.book_selling.entity.BookSelling;
import com.example.book_selling.vo.BookSellingResponse;

public interface BookSellingService {

	public BookSellingResponse AddBook(List<BookSelling> bookList);

	public BookSellingResponse SearchBookByCategory(String str);
	
	public BookSellingResponse SearchBookContaining(String str);
	
//	public BookSellingResponse UpdateBook(BookSelling book);
	
//	public BookSellingResponse OrderBook(Map<String,Integer> map);
	
}
