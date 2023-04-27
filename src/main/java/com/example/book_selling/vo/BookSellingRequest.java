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

	@JsonProperty(value = "search_keyword")
	private String str;

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	
	//搜尋功能(消費者/廠商)
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
	
	//更新書籍
	//+searchIsbn
	@JsonProperty(value = "update_price")
	private int updatePrice;
	
	@JsonProperty(value = "update_category")
	private String updateCategory;
	
	@JsonProperty(value = "update_in_stock")
	private int updateInStock;

	public int getUpdatePrice() {
		return updatePrice;
	}

	public void setUpdatePrice(int updatePrice) {
		this.updatePrice = updatePrice;
	}

	public String getUpdateCategory() {
		return updateCategory;
	}

	public void setUpdateCategory(String updateCategory) {
		this.updateCategory = updateCategory;
	}

	public int getUpdateInStock() {
		return updateInStock;
	}

	public void setUpdateInStock(int updateInStock) {
		this.updateInStock = updateInStock;
	}

}
