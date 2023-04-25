package com.example.book_selling.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.example.book_selling.service.ifs.BookSellingService;

@RestController
public class BookSellingController {
	
	@Autowired
	private BookSellingService bookService;

}
