package com.example.book_selling.service.ifs;

import java.util.List;
import java.util.Map;

import com.example.book_selling.entity.BookSelling;
import com.example.book_selling.vo.BookSellingResponse;

public interface BookSellingService {
	//2
	public BookSellingResponse AddBook(List<BookSelling> bookList);
	//3
	public BookSellingResponse SearchBookByCategory(String str);
	//4
	public BookSellingResponse SearchBookContaining(Boolean isCustomer, String str);
//	public BookSellingResponse SearchBookContaining(Boolean isCustomer, String isbn, String name, String author);
	//5
//	public BookSellingResponse UpdateBook(BookSelling book);
	public BookSellingResponse UpdateBook(String isbn, int price, String category, int inStock);
	//6
	public BookSellingResponse OrderBook(Map<String,Integer> map);
	
	public BookSellingResponse SaleRank();

	
}
