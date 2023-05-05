package com.example.book_selling.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class SearchResultConvert {

	private String isbn;
	private String name;
	private String author;
	private String category;
	private int price;
	private int inStock;
	private int soldQuantity;

	public SearchResultConvert() {
		super();
	}


	// for Selling (六)
	public SearchResultConvert(String isbn, String name, String author, int price) {
		super();
		this.isbn = isbn;
		this.name = name;
		this.author = author;
		this.price = price;
	}

	// Update Show(合併顯示) i書作分價庫
	public SearchResultConvert(String isbn, String name, String author, String category, int price, int inStock) {
		super();
		this.isbn = isbn;
		this.name = name;
		this.author = author;
		this.category = category;
		this.price = price;
		this.inStock = inStock;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getInStock() {
		return inStock;
	}

	public void setInStock(int inStock) {
		this.inStock = inStock;
	}

	public int getSoldQuantity() {
		return soldQuantity;
	}

	public void setSoldQuantity(int soldQuantity) {
		this.soldQuantity = soldQuantity;
	}

}
