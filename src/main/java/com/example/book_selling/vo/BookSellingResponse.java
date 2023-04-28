package com.example.book_selling.vo;

import java.util.List;

import com.example.book_selling.entity.BookSelling;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class BookSellingResponse {

	
	private BookSelling bookselling;
	private SearchResultConvert searchConvert;
	private List<SearchResultConvert> searchConverList;
	private List<BookSelling> bookList;
	private String message;
	private int sum; // 販售--計算總價
	private List<OrderResultConvert> resultConver; // 販售--結果+數量
//	private List<Object[]> obList; //用來存放JPQL的結果
//	private Map<List<Object[]>,Integer> orderList; //販售--訂單內容與數量(顯示部分資訊)

	public BookSellingResponse() {
		super();
	}

	public BookSellingResponse(String message) {
		super();
		this.message = message;
	}

	public BookSellingResponse(List<OrderResultConvert> resultConver, int sum, String message) {
		super();
		this.message = message;
		this.sum = sum;
		this.resultConver = resultConver;
	}

	public BookSellingResponse(SearchResultConvert searchConvert, String message) {
		super();
		this.searchConvert = searchConvert;
		this.message = message;
	}

	public BookSellingResponse(List<SearchResultConvert> searchConverList) {
		super();
		this.searchConverList = searchConverList;
	}

	public BookSellingResponse(String message, List<SearchResultConvert> searchConverList) {
		super();
		this.searchConverList = searchConverList;
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

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	public SearchResultConvert getSearchResultConvert() {
		return searchConvert;
	}

	public void setSearchResultConvert(SearchResultConvert searchResultConvert) {
		this.searchConvert = searchResultConvert;
	}

	public SearchResultConvert getSearchConvert() {
		return searchConvert;
	}

	public void setSearchConvert(SearchResultConvert searchConvert) {
		this.searchConvert = searchConvert;
	}

	public List<SearchResultConvert> getSearchConverList() {
		return searchConverList;
	}

	public void setSearchConverList(List<SearchResultConvert> searchConverList) {
		this.searchConverList = searchConverList;
	}

	public List<OrderResultConvert> getResultConver() {
		return resultConver;
	}

	public void setResultConver(List<OrderResultConvert> resultConver) {
		this.resultConver = resultConver;
	}

}
