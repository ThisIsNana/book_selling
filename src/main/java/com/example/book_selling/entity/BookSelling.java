package com.example.book_selling.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "book")
public class BookSelling {

	@Id
	@Column(name = "isbn")
	private String isbn;

	@Column(name = "name")
	private String name;

	@Column(name = "author")
	private String author;

	@Column(name = "price")
	private int price;

	@Column(name = "category")
	private String category;

	@Column(name = "in_stock")
	private int inStock;

	@Column(name = "sold_quantity")
	private int soldQuantity;

	public BookSelling() {
		super();
	}

	public BookSelling(String isbn, String name, String author, int price, String category, int inStock) {
		super();
		this.isbn = isbn;
		this.name = name;
		this.author = author;
		this.price = price;
		this.category = category;
	}

	public BookSelling(String isbn, String name, String author, int price, String category, int inStock,
			int soldQuantity) {
		super();
		this.isbn = isbn;
		this.name = name;
		this.author = author;
		this.price = price;
		this.category = category;
		this.inStock = inStock;
		this.soldQuantity = soldQuantity;
	}

	// =======G&S

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
