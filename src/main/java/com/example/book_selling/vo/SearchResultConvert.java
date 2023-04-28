package com.example.book_selling.vo;

public class SearchResultConvert {

	private String isbn;
	private String name;
	private String author;
	private String category;
	private int price;
	private int inStock;
	private int soldQuantity;
	private int quantity;

	public SearchResultConvert() {
		super();
	}

	// Order (六) i書作價數 (重複格式所以把作者往後移)
	public SearchResultConvert(String isbn, String name, int price, String author, int quantity) {
		super();
		this.isbn = isbn;
		this.name = name;
		this.price = price;
		this.author = author;
		this.quantity = quantity;
	}

	// for search category(二) I書作價庫
	public SearchResultConvert(String isbn, String name, String author, int price, int inStock) {
		super();
		this.isbn = isbn;
		this.name = name;
		this.author = author;
		this.price = price;
		this.inStock = inStock;
	}

	// for customers (三之一) I書作價
	// for Selling (六)
	public SearchResultConvert(String isbn, String name, String author, int price) {
		super();
		this.isbn = isbn;
		this.name = name;
		this.author = author;
		this.price = price;
	}

	// for Supplier(三之二) I書作價庫銷
	public SearchResultConvert(String isbn, String name, String author, int price, int inStock, int soldQuantity) {
		super();
		this.isbn = isbn;
		this.name = name;
		this.author = author;
		this.price = price;
		this.inStock = inStock;
		this.soldQuantity = soldQuantity;
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

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
