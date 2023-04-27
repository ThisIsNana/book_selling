package com.example.book_selling.vo;

import java.util.List;

import com.example.book_selling.entity.BookSelling;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BookSellingRequest {

	@JsonProperty(value = "add_book_list")
	private List<BookSelling> bookList;

	public List<BookSelling> getBookList() {
		return bookList;
	}

	public void setBookList(List<BookSelling> bookList) {
		this.bookList = bookList;
	}

	@JsonProperty(value = "search_category_keyword")
	private String str;

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	@JsonProperty(value = "is_customer")
	private Boolean isCustomer;

	@JsonProperty(value = "search_keyword_isbn")
	private String searchIsbn;
	
	@JsonProperty(value = "search_keyword_name")
	private String searchName;

	@JsonProperty(value = "search_keyword_author")
	private String searchAuthor;

	public Boolean getIsCustomer() {
		return isCustomer;
	}

	public void setIsCustomer(Boolean isCustomer) {
		this.isCustomer = isCustomer;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public String getSearchAuthor() {
		return searchAuthor;
	}

	public void setSearchAuthor(String searchAuthor) {
		this.searchAuthor = searchAuthor;
	}

	public String getSearchIsbn() {
		return searchIsbn;
	}

	public void setSearchIsbn(String searchIsbn) {
		this.searchIsbn = searchIsbn;
	}
	
	@JsonProperty(value = "update")
	private BookSelling newInfo;

	public BookSelling getNewInfo() {
		return newInfo;
	}

	public void setNewInfo(BookSelling newInfo) {
		this.newInfo = newInfo;
	}


}
