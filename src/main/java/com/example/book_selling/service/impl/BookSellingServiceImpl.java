package com.example.book_selling.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.book_selling.constants.RtnCode;
import com.example.book_selling.entity.BookSelling;
import com.example.book_selling.repository.BookSellingDAO;
import com.example.book_selling.service.ifs.BookSellingService;
import com.example.book_selling.vo.BookSellingResponse;
import com.example.book_selling.vo.SearchResultConvert;

@Service
public class BookSellingServiceImpl implements BookSellingService {

	@Autowired
	private BookSellingDAO bookDAO;

	// 新增圖書(ISBN、書名、作者、價格、分類為必填；銷售量、庫存預設為0，庫存可填可不填。
	@Override
	public BookSellingResponse AddBook(List<BookSelling> bookList) {
		if (bookList == null || CollectionUtils.isEmpty(bookList)) {
			return new BookSellingResponse(RtnCode.CANNOT_EMTPY.getMessage());
		}
		List<String> isbnList = new ArrayList<>();
		for (BookSelling book : bookList) {
			// 檢查ISBN
			String isbn10Pattern = "\\d{10}";
			String isbn13Pattern = "\\d{13}";
//			String isbnPattern = "^(?:97[89]-)?\\\\d{1,5}-\\\\d+-\\\\d+-[\\\\dX]$"; //讚嘆AI
			if (!book.getIsbn().matches(isbn10Pattern) && !book.getIsbn().matches(isbn13Pattern)) {
				return new BookSellingResponse(RtnCode.DATA_ERROR.getMessage());
			}
			// 檢查String類
			if (!StringUtils.hasText(book.getIsbn()) || !StringUtils.hasText(book.getName())
					|| !StringUtils.hasText(book.getAuthor()) || !StringUtils.hasText(book.getCategory())) {
				return new BookSellingResponse(RtnCode.CANNOT_EMTPY.getMessage());
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
		if (dbList.size() == bookList.size()) {
			return new BookSellingResponse(RtnCode.ALREADY_PRESENT.getMessage());
		}
		List<String> dbIdList = new ArrayList<>();
		for (BookSelling dbBook : dbList) {
			dbIdList.add(dbBook.getIsbn());
		}
		if (bookDAO.findAllById(dbIdList).size() > 0) {
			for (BookSelling book : bookList) {
				if (!dbIdList.contains(book.getIsbn())) {
					saveList.add(book);
				}
			}
			bookDAO.saveAll(saveList);
		}
		bookDAO.saveAll(bookList);
		return new BookSellingResponse(saveList, RtnCode.SUCCESSFUL.getMessage());
	}

	// 功能二：搜尋分類!
	@Override
	public BookSellingResponse SearchBookByCategory(String str) {
		// 防呆
		if (!StringUtils.hasText(str)) {
			return new BookSellingResponse(RtnCode.CANNOT_EMTPY.getMessage());
		}
		// 使用JPA自定義方法
		List<BookSelling> resultList = bookDAO.findByCategoryContaining(str);
		// 如果抓不到數據
		if (resultList.isEmpty()) {
			return new BookSellingResponse(RtnCode.NOT_FOUND.getMessage());
		}
		List<SearchResultConvert> searchConvert = new ArrayList<>();
		for (BookSelling result : resultList) {
			SearchResultConvert sfc = new SearchResultConvert(result.getIsbn(), result.getName(), result.getAuthor(),
					result.getPrice(), result.getInStock());
			searchConvert.add(sfc);
		}

		// 成功就顯示結果
		return new BookSellingResponse(searchConvert);
	}

	// 功能三：搜尋字串(JPQL)
//	@Override
//	public BookSellingResponse SearchBookContaining(Boolean isCustomer, String str) {
//		// 防呆(身分未指定 或 搜尋全空白或null)
//		if (isCustomer == null) {
//			return new BookSellingResponse(RtnCode.IDENTIFY_CANNOT_EMTPY.getMessage());
//		}
//		if (!StringUtils.hasText(str)) {
//			return new BookSellingResponse(RtnCode.CANNOT_EMTPY.getMessage());
//		}	
//		// 使用JPQL去搜尋結果
//		List<Object[]> bookResult = new ArrayList<>();
//		if(isCustomer == true) {
//			 bookResult = bookDAO.SearchAllByKeywordForCustomer(str);
//		}else {
//			 bookResult = bookDAO.SearchAllByKeywordForSupplier(str);
//		}
//		if(CollectionUtils.isEmpty(bookResult)) {
//			return new BookSellingResponse(RtnCode.NOT_FOUND.getMessage());
//		}
//		return new BookSellingResponse(RtnCode.SUCCESSFUL.getMessage(),bookResult);
//	}
	// 功能三：搜尋字串(JPA)
	@Override
	public BookSellingResponse SearchBookContaining(Boolean isCustomer, String isbn, String name, String author) {
		// 防呆(身分未指定 或 搜尋全空白或null)
		if (isCustomer == null) {
			return new BookSellingResponse(RtnCode.IDENTIFY_CANNOT_EMTPY.getMessage());
		}
		if (!StringUtils.hasText(isbn) && !StringUtils.hasText(name) && !StringUtils.hasText(author)) {
			return new BookSellingResponse(RtnCode.CANNOT_EMTPY.getMessage());
		}
		// 嘗試使用個別方法去搜尋再加SET ==> 邏輯過長且重複執行DAO ==> 放棄
		// 使用JPA去搜尋
		List<BookSelling> resultList = bookDAO.findAllByIsbnContainingOrNameContainingOrAuthorContaining(isbn, name,
				author);
		List<SearchResultConvert> searchConvert = new ArrayList<>();
		for (BookSelling result : resultList) {
			if (isCustomer == true) { // 消費者顯示 I書作價
				SearchResultConvert sfc = new SearchResultConvert(result.getIsbn(), result.getName(),
						result.getAuthor(), result.getPrice());
				searchConvert.add(sfc);
			} else { // 廠商顯示 i書作價銷庫
				SearchResultConvert sfc = new SearchResultConvert(result.getIsbn(), result.getName(),
						result.getAuthor(), result.getPrice(), result.getInStock(), result.getSoldQuantity());
				searchConvert.add(sfc);
			}
		}
		return new BookSellingResponse(RtnCode.SUCCESSFUL.getMessage(), searchConvert);
	}

	// 功能四: 更新庫存、價格或分類
	@Override
	public BookSellingResponse UpdateBook(String isbn, int price, String category, int inStock) {
//	public BookSellingResponse UpdateBook(BookSelling book) {
		// 防呆-空白部分
		if (!StringUtils.hasText(isbn)) {
			return new BookSellingResponse(RtnCode.ISBN_CANNOT_EMTPY.getMessage());
		}
		// 查無書籍
		Optional<BookSelling> op = bookDAO.findById(isbn);
		if (!op.isPresent()) {
			return new BookSellingResponse(RtnCode.NOT_FOUND.getMessage());
		}
		BookSelling updateBook = op.get();
		// 檢查進貨後數量必須比庫存多 --> 非0或大於庫存就set
		int opInStock = updateBook.getInStock();
		if (opInStock > inStock) {
			return new BookSellingResponse(RtnCode.INT_ERROR.getMessage());
		} else if (opInStock != inStock || inStock != 0) {
			updateBook.setInStock(inStock);
		}
		// 檢查價格格式 --> 有變更就set
		if (price <= 0) {
			return new BookSellingResponse(RtnCode.INT_ERROR.getMessage());
		} else {
			updateBook.setPrice(price);
		}
		// 檢查分類是否沒修改
		// 步驟1/3--抓出字串+去頭去尾+分割+加入List
		String opCate = updateBook.getCategory().substring(1, updateBook.getCategory().length() - 1);
		String bookCate = category.substring(1, category.length() - 1);
		List<String> opCateList = Arrays.asList(opCate.split(", "));
		List<String> bookCateList = Arrays.asList(bookCate.split(", "));
		// 步驟2/3--先比較List長度，只要不同就直接上傳
		if (opCateList.size() != bookCateList.size()) {
			updateBook.setCategory(category);
			bookDAO.save(updateBook);
			return new BookSellingResponse(updateBook, RtnCode.SUCCESSFUL.getMessage());
		}
		// 步驟3/3--同樣長度的[]-->比較內容是否完全一樣
		int count = 0;
		for (String bookArr : bookCateList) {
			if (opCateList.contains(bookArr)) {
				count++;
				continue;
			}
		}
		// 假如新的分類完全等於原始的分類、價格一樣、庫存一樣 => 錯誤訊息"無項目變更"
		if (count == opCateList.size() && op.get().getInStock() == inStock && op.get().getPrice() == price) {
			return new BookSellingResponse(RtnCode.NO_CHANGE.getMessage());
		}
		updateBook.setCategory(category);
		bookDAO.save(updateBook);
		// 轉換顯示結果
		SearchResultConvert sfc = new SearchResultConvert(updateBook.getIsbn(), updateBook.getName(),
				updateBook.getAuthor(), updateBook.getPrice(), updateBook.getInStock());
		return new BookSellingResponse(sfc, RtnCode.SUCCESSFUL.getMessage());
	}

	// 功能五：銷售書籍/購買書籍+計算
	@Override
	public BookSellingResponse OrderBook(Map<String, Integer> orderMap) {
		// 防呆
		if (CollectionUtils.isEmpty(orderMap)) {
			return new BookSellingResponse(RtnCode.CANNOT_EMTPY.getMessage());
		}
		// 取map的isbn資料
		List<String> isbnList = new ArrayList<>();
		for (Entry<String, Integer> order : orderMap.entrySet()) {
			if (order.getValue() < 0 || order.getValue() == null || order.getKey() == null) {
				return new BookSellingResponse(RtnCode.INT_ERROR.getMessage());
			}
			isbnList.add(order.getKey());
		}
		// 用JPA將結果取出
		List<BookSelling> resultList = bookDAO.findAllById(isbnList);
		List<BookSelling> saveList = new ArrayList<>();
		// 用來儲存只顯示某些項目的搜尋結果+數量
		List<SearchResultConvert> resultConver = new ArrayList<>();
		int sum = 0;
		for (BookSelling result : resultList) {
			for (Entry<String, Integer> order : orderMap.entrySet()) {
				if (result.getInStock() < order.getValue()) {
					// 庫存不足
					return new BookSellingResponse(RtnCode.NOT_IN_STOCK.getMessage());
				}
				if (result.getIsbn().equals(order.getKey())) {
					// 計算價格
					int price = result.getPrice();
					int quantity = order.getValue();
					sum += price * quantity;
					// 修改庫存及銷售量
					result.setInStock(result.getInStock() - 1);
					result.setSoldQuantity(result.getSoldQuantity() + 1);
					saveList.add(result);
					// 轉換格式
					SearchResultConvert sfc = new SearchResultConvert(result.getIsbn(), result.getName(),
							result.getPrice(), result.getAuthor(), order.getValue());
					resultConver.add(sfc);
				}
			}
		}
		bookDAO.saveAll(saveList);
		return new BookSellingResponse(resultConver, sum, RtnCode.SUCCESSFUL.getMessage());
	}

	@Override
	public BookSellingResponse SaleRank() {
		List<BookSelling> resultList = bookDAO.findTop5ByOrderBySoldQuantityDesc();
		//轉換銷售顯示
		List<SearchResultConvert> resultConver = new ArrayList<>();
		for(BookSelling result:resultList) {
			SearchResultConvert sfc = new SearchResultConvert(result.getIsbn(), result.getName(),
					result.getAuthor(), result.getPrice());
			resultConver.add(sfc);
		}
		return new BookSellingResponse(RtnCode.SUCCESSFUL.getMessage(),resultConver);
	}

}
