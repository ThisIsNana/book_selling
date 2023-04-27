package com.example.book_selling.vo;

import java.util.List;
import java.util.Map;

import com.example.book_selling.entity.BookSelling;

public class BookSellingResponse {

	
	
	private BookSelling bookselling;
	private List<SearchForCustomer> searchForC;
	private List<BookSelling> bookList;
	private List<Object[]> obList; //用來存放JPQL的結果
	private String message;
	private int sum; //販售--計算總價
	private Map<List<Object[]>,Integer> orderList; //販售--訂單內容與數量(顯示部分資訊)

	public BookSellingResponse() {
		super();
	}

	public BookSellingResponse(String message) {
		super();
		this.message = message;
	}


	public BookSellingResponse(List<SearchForCustomer> searchForC) {
		super();
		this.searchForC = searchForC;
	}

	public BookSellingResponse( Map<List<Object[]>, Integer> orderList, int sum, String message) {
		super();
		this.message = message;
		this.sum = sum;
		this.orderList = orderList;
	}

	public BookSellingResponse(String message, List<Object[]> obList) {
		super();
		this.obList = obList;
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

	public List<Object[]> getObList() {
		return obList;
	}

	public void setObList(List<Object[]> obList) {
		this.obList = obList;
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	public Map<List<Object[]>, Integer> getOrderList() {
		return orderList;
	}

	public void setOrderList(Map<List<Object[]>, Integer> orderList) {
		this.orderList = orderList;
	}

	public List<SearchForCustomer> getSearchForC() {
		return searchForC;
	}

	public void setSearchForC(List<SearchForCustomer> searchForC) {
		this.searchForC = searchForC;
	}

	

}
