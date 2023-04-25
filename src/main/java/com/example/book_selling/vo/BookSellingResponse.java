package com.example.book_selling.vo;

import java.util.List;

import com.example.book_selling.entity.BookSelling;

public class BookSellingResponse {

	private BookSelling bookselling;
	private List<BookSelling> bookList;
	private String message;

	public BookSellingResponse() {
		super();
	}

	public BookSellingResponse(String message) {
		super();
		this.message = message;
	}

	public BookSellingResponse(BookSelling bookselling, String message) {
		super();
		this.bookselling = bookselling;
		this.message = message;
	}

	public BookSellingResponse(List<BookSelling> bookList, String message) {
		super();
		this.bookList = bookList;
		this.message = message;
	}

	public BookSelling getBookselling() {
		return bookselling;
	}

	public void setBookselling(BookSelling bookselling) {
		this.bookselling = bookselling;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<BookSelling> getBookList() {
		return bookList;
	}

	public void setBookList(List<BookSelling> bookList) {
		this.bookList = bookList;
	}

}
