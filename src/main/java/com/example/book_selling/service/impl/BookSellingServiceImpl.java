package com.example.book_selling.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.book_selling.constants.RtnCode;
import com.example.book_selling.entity.BookSelling;
import com.example.book_selling.repository.BookSellingDAO;
import com.example.book_selling.service.ifs.BookSellingService;
import com.example.book_selling.vo.BookSellingResponse;

@Service
public class BookSellingServiceImpl implements BookSellingService {

	@Autowired
	private BookSellingDAO bookDAO;

	@Override
	public BookSellingResponse AddBook(List<BookSelling> bookList) {
		if (bookList == null || CollectionUtils.isEmpty(bookList)) {
			return new BookSellingResponse(RtnCode.CANNOT_EMTPY.getMessage());
		}
		List<String> isbnList = new ArrayList<>();
		for (BookSelling book : bookList) {
			// 檢查String類
			if (!StringUtils.hasText(book.getIsbn()) 
					|| !StringUtils.hasText(book.getName())
					|| !StringUtils.hasText(book.getAuthor()) 
					|| !StringUtils.hasText(book.getCategory())) {
				return new BookSellingResponse(RtnCode.CANNOT_EMTPY.getMessage());
			}
			// 檢查ISBN
			String isbnPattern = "^(?:97[89]-)?\\\\d{1,5}-\\\\d+-\\\\d+-[\\\\dX]$";
			if (!isbnPattern.matches(book.getIsbn())) {
				return new BookSellingResponse(RtnCode.DATA_ERROR.getMessage());
			}
			// 檢查INT類
			if (book.getPrice() <= 0 || book.getInStock() < 0 || book.getSoldQuantity() < 0) {
				return new BookSellingResponse(RtnCode.DATA_ERROR.getMessage());
			}
			isbnList.add(book.getIsbn());
		}
		// 檢查重複
		List<BookSelling> saveList = new ArrayList<>();
		List<BookSelling> dbList = bookDAO.findAllById(isbnList);
		if (dbList.size() == 0) {
			return new BookSellingResponse(RtnCode.ALREADY_PRESENT.getMessage());
		}
		List<String> dbIdList = new ArrayList<>();
		for(BookSelling dbBook :dbList) {
			dbIdList.add(dbBook.getIsbn());
		}
		if(bookDAO.findAllById(dbIdList).size() > 0) {
			for( BookSelling book:bookList) {
				if(!dbIdList.contains(book.getIsbn())) {
					saveList.add(book);
				}
			}
			bookDAO.saveAll(saveList);
		}
		bookDAO.saveAll(bookList);
		return new BookSellingResponse(saveList, RtnCode.SUCCESSFUL.getMessage());
	}

	@Override
	public BookSellingResponse  SearchBookByCategory(String str) {
		if(!StringUtils.hasText(str)) {
			return new BookSellingResponse(RtnCode.CANNOT_EMTPY.getMessage());
		}
		List<BookSelling> result = bookDAO.findByCategoryContaining(str);
		if(result.isEmpty()) {
			return new BookSellingResponse(RtnCode.NOT_FOUND.getMessage());
		}
		return new BookSellingResponse(result, RtnCode.SUCCESSFUL.getMessage());
	}

	@Override
	public BookSellingResponse SearchBookContaining(String str) {
		if(!StringUtils.hasText(str)) {
			return new BookSellingResponse(RtnCode.CANNOT_EMTPY.getMessage());
		}
		List<BookSelling> result = bookDAO.findByIdContainingOrNameContainingOrAuthorContaining(str);
		if(result.isEmpty()) {
			
		}
		
		return null;
	}

}
