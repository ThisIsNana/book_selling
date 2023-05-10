package com.example.book_selling.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.book_selling.constants.RtnCode;
import com.example.book_selling.entity.BookSelling;
import com.example.book_selling.repository.BookSellingDAO;
import com.example.book_selling.service.ifs.BookSellingService;
import com.example.book_selling.vo.BookSellingResponse;
import com.example.book_selling.vo.OrderResultConvert;
import com.example.book_selling.vo.SearchResultConvert;

@Service
public class BookSellingServiceImpl implements BookSellingService {
	
	private Logger logger = LoggerFactory.getLogger(getClass()); //slf4j

	@Autowired
	private BookSellingDAO bookDAO;

	// 新增圖書(ISBN、書名、作者、價格、分類為必填；銷售量、庫存預設為0，庫存可填可不填。
	@Override
	public BookSellingResponse addBook(List<BookSelling> bookList) {
		if (CollectionUtils.isEmpty(bookList)) {
			return new BookSellingResponse(RtnCode.CANNOT_EMTPY.getMessage());
		}
		Set<String> isbnList = new HashSet<>();
		for (BookSelling book : bookList) {
			// 檢查ISBN
			String isbn10Pattern = "\\d{10}|\\d{13}";
//			String isbnPattern = "^(?:97[89]-)?\\\\d{1,5}-\\\\d+-\\\\d+-[\\\\dX]$"; //讚嘆AI
			if (!book.getIsbn().matches(isbn10Pattern)) {
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

		// 抓出資料庫的ISBN資料
		List<BookSelling> resultList = bookDAO.findAllById(isbnList);
		List<String> resList =  new ArrayList<>();		
		for(BookSelling res: resultList) {
			resList.add(res.getIsbn());
		}
		
		// 全部都重複 --> 錯誤訊息
		if (resultList.size() == bookList.size()) {
			return new BookSellingResponse(RtnCode.ALREADY_PRESENT.getMessage());
		}

		// 資料庫抓不出東西時 --> 直接新增並結束
		if (resultList.size() == 0) {
			bookDAO.saveAll(new HashSet<>(bookList));
			return new BookSellingResponse(bookList, RtnCode.SUCCESSFUL.getMessage());
		}
		
		// 部分重複 --> 重複的不新增		
		String resStr = String.join(",", resList);
		List<BookSelling> saveList = new ArrayList<>();
		for (BookSelling book : bookList) {
			if(resStr.contains(book.getIsbn())) {
				continue;
			}
			saveList.add(book);
		}
		logger.info("add book finished.");
		bookDAO.saveAll(saveList);
		return new BookSellingResponse(saveList, RtnCode.SUCCESSFUL.getMessage());
	}

	
	// 功能二：搜尋分類!
	@Override
	public BookSellingResponse searchBookByCategory(String str) {
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
		List<BookSelling> searchConvert = new ArrayList<>();
		for (BookSelling result : resultList) {
			BookSelling bk = new BookSelling(result.getIsbn(), result.getName(), result.getAuthor(), result.getPrice(),
					result.getInStock());
			searchConvert.add(bk);
		}

		// 成功就顯示結果
		return new BookSellingResponse(searchConvert, RtnCode.SUCCESSFUL.getMessage());
	}
	
	
	// 功能三：搜尋字串(JPQL＆JPA皆可)
	@Override
//	public BookSellingResponse SearchBookContaining(Boolean isCustomer, String isbn, String name, String author) {
	public BookSellingResponse searchBookContaining(Boolean isCustomer, String str) {

		// 防呆(身分未指定 或 搜尋全空白或null)
		if (isCustomer == null) {
			return new BookSellingResponse(RtnCode.IDENTIFY_CANNOT_EMTPY.getMessage());
		}
		if (!StringUtils.hasText(str)) {
			return new BookSellingResponse(RtnCode.CANNOT_EMTPY.getMessage());
		}
		
//		if (!StringUtils.hasText(isbn) && !StringUtils.hasText(name) && !StringUtils.hasText(author)) {
//			return new BookSellingResponse(RtnCode.CANNOT_EMTPY.getMessage());
//		}
		//老師建議可用三元寫法:
//		String name = StringUtils.hasText(name) ? name : null;
//		String isbn = StringUtils.hasText(isbn) ? isbn : null;
//		String author = StringUtils.hasText(author) ? author : null;
//		logger.info("search for..."); // 搭配logger使用，有加的會添加進日誌裡面
		
		// 嘗試使用個別方法去搜尋再加SET ==> 邏輯過長且重複執行DAO ==> 放棄
		// 使用JPA去搜尋
//		List<BookSelling> resultList 
//			= bookDAO.findAllByIsbnContainingOrNameContainingOrAuthorContaining(isbn, name, author);
		
		//將搜尋的關鍵字設定為正則表達式(以空格區分)
		List<String> keyword = new ArrayList<>();
		keyword.add(str.split(" ").toString());
		String newStr = String.join("|",keyword);
		
		List<BookSelling> resultList = bookDAO.findAllByContainingRegexp(newStr);
		List<BookSelling> searchConvert = new ArrayList<>();
		for (BookSelling result : resultList) {
			if (isCustomer == true) { // 消費者顯示 I書作價
				BookSelling bk = new BookSelling(result.getIsbn(), result.getName(), result.getAuthor(),
						result.getPrice());
				searchConvert.add(bk);
			} else { // 廠商顯示 i書作價銷庫
				BookSelling bk = new BookSelling(result.getIsbn(), result.getName(), result.getAuthor(),
						result.getPrice(), result.getSoldQuantity(), result.getInStock());
				searchConvert.add(bk);
			}
		}
		if (CollectionUtils.isEmpty(searchConvert)) {
			return new BookSellingResponse(searchConvert, RtnCode.NOT_FOUND.getMessage());
		}
		return new BookSellingResponse(searchConvert, RtnCode.SUCCESSFUL.getMessage());
	}
	
	//功能4-1 進貨Map<isbn, 進貨數量>
	@Override
	public BookSellingResponse updateBook(Map<String, Integer> inputMap) {
		if(CollectionUtils.isEmpty(inputMap)) {
			return new BookSellingResponse(RtnCode.CANNOT_EMTPY.getMessage());
		}
		
		//收集isbn
		List<String> inputIsbn = new ArrayList<>();
		for(Entry<String, Integer> input :inputMap.entrySet()) {
			//防呆--內容不可空白或為負值，有則忽略
			if(!StringUtils.hasText(input.getKey()) || input.getValue() <= 0 ) {
				continue;
			}
			inputIsbn.add(input.getKey()); 
		}
		
		//找出相符的isbn (0結果 => 「無相符資料」)
		List<BookSelling> resultList = bookDAO.findAllById(inputIsbn);
		if(CollectionUtils.isEmpty(resultList)) {
			return new BookSellingResponse(RtnCode.NOT_FOUND.getMessage());
		}
		
		//相同isbn就更新庫存(把進貨數量加上去)
		List<BookSelling> updateList = new ArrayList<>();
		for(Entry<String, Integer> input :inputMap.entrySet()) {
			for(BookSelling result : resultList) {
				if(input.getKey().equals(result.getIsbn())) {
				result.setInStock(result.getInStock() + input.getValue());
				updateList.add(result);
				}
			}
		}
		
		bookDAO.saveAll(updateList);
		return new BookSellingResponse(updateList, RtnCode.SUCCESSFUL.getMessage());
	}

	//功能4-2 更新價格
	@Override
	public BookSellingResponse updateBook(String isbn, int price) {
		//防呆
		if(!StringUtils.hasText(isbn)) {
			return new BookSellingResponse(RtnCode.CANNOT_EMTPY.getMessage());
		}
		if(price <= 0) {
			return new BookSellingResponse(RtnCode.DATA_ERROR.getMessage());
		}
		
		//確認isbn存不存在
		Optional<BookSelling> op = bookDAO.findById(isbn);
		if(!op.isPresent()) {
			return new BookSellingResponse(RtnCode.NOT_FOUND.getMessage());
		}
		
		BookSelling result = op.get();
		result.setPrice(price);
		return new BookSellingResponse(result, RtnCode.SUCCESSFUL.getMessage());
	}

	//功能4-3 更新分類
	@Override
	public BookSellingResponse updateBook(String isbn, String category) {
		//防呆
		if(!StringUtils.hasText(isbn) || !StringUtils.hasText(category)) {
			return new BookSellingResponse(RtnCode.CANNOT_EMTPY.getMessage());
		}
		
		//確認isbn存不存在
		Optional<BookSelling> op = bookDAO.findById(isbn);
		if(!op.isPresent()) {
			return new BookSellingResponse(RtnCode.NOT_FOUND.getMessage());
		}
		
		BookSelling result = op.get(); 
		
		//分類去掉空格+分割+Set去比較
		Set<String> cateSet = new HashSet<>();
		cateSet.addAll(Arrays.asList(category.replace(" ","").split(",")));
		
		Set<String> resSet = new HashSet<>();
		resSet.addAll(Arrays.asList(result.getCategory().split(",")));
			
		if(cateSet.equals(resSet) == true) {
			return new BookSellingResponse(RtnCode.NO_CHANGE.getMessage());
		}
		
		List<String> newCate = new ArrayList<>();
		for(String cate : cateSet) {
			if(!StringUtils.hasText(cate)) {
				continue;
			}
			newCate.add(cate);
		}
		
		//把List內容串成字串~
		String save = String.join(",", newCate);

		result.setCategory(save);
		return new BookSellingResponse(RtnCode.SUCCESSFUL.getMessage());
	}
	
	//原本寫法(把所有更新合在一起 -> 不利前後端維護，改為單一功能)
//	@Override
//	public BookSellingResponse updateBook(String isbn, int price, String category, int inStock) {
////	public BookSellingResponse UpdateBook(BookSelling book) {
//		// 防呆-空白部分
//		if (!StringUtils.hasText(isbn)) {
//			return new BookSellingResponse(RtnCode.ISBN_CANNOT_EMTPY.getMessage());
//		}
//
//		// 查無書籍
//		Optional<BookSelling> op = bookDAO.findById(isbn);
//		if (!op.isPresent()) {
//			return new BookSellingResponse(RtnCode.NOT_FOUND.getMessage());
//		}
//		BookSelling updateBook = op.get();
//
//		// 檢查進貨後數量必須比庫存多 --> 大於就set
//		int opInStock = updateBook.getInStock();
//		if (opInStock > inStock) {
//			return new BookSellingResponse(RtnCode.INT_ERROR.getMessage());
//		} else {
//			updateBook.setInStock(inStock);
//		}
//
//		// 檢查價格格式 --> 有變更就set
//		if (price <= 0) {
//			return new BookSellingResponse(RtnCode.INT_ERROR.getMessage());
//		} else {
//			updateBook.setPrice(price);
//		}
//
//		// 檢查分類是否沒修改或修改不正確
//		// 1. 抓出字串+分割+加入List & 把list.toString後的產物[XX,XX]去頭去尾 => 準備好set上去的格式
//		List<String> opCateList = Arrays.asList(updateBook.getCategory().split(", "));
//
//		List<String> bookCateList = new ArrayList<>();
//		bookCateList.addAll(Arrays.asList(category.split(", ")));
//		bookCateList.removeIf(String::isBlank);
//
//		String cateStr = bookCateList.toString().substring(1, bookCateList.toString().length() - 1);
//
//		// 2. 先比較List長度 => 不同就更新
//		if (opCateList.size() != bookCateList.size()) {
//			updateBook.setCategory(cateStr);
//			bookDAO.save(updateBook);
//			return new BookSellingResponse(updateBook, RtnCode.SUCCESSFUL.getMessage());
//		}
//
//		// 3. 同樣長度的[]-->比較內容是否完全一樣
//		int count = 0;
//		for (String bookArr : bookCateList) {
//			if (opCateList.contains(bookArr)) {
//				count++;
//			}
//		}
//
//		// 4. 假如新的分類完全等於原始的分類、價格一樣、庫存一樣 => 錯誤訊息"資料未修改"
//		if (count == opCateList.size() && op.get().getInStock() == updateBook.getInStock()
//				&& op.get().getPrice() == updateBook.getPrice()) {
//			return new BookSellingResponse(RtnCode.NO_CHANGE.getMessage());
//		}
//		updateBook.setCategory(cateStr);
//		bookDAO.save(updateBook);
//
//		// 轉換顯示結果(直接用Entity建構方法來用(by老師貢欸))
//		BookSelling bk = new BookSelling(updateBook.getIsbn(), updateBook.getName(), updateBook.getAuthor(),
//				updateBook.getPrice(), updateBook.getInStock());
//		return new BookSellingResponse(bk, RtnCode.SUCCESSFUL.getMessage());
//	}

	// 功能五：銷售書籍/購買書籍+計算
	@Override
	public BookSellingResponse orderBook(Map<String, Integer> orderMap) {
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
		List<OrderResultConvert> resultConver = new ArrayList<>();
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
					OrderResultConvert orc = new OrderResultConvert(result.getIsbn(), result.getName(),
							result.getAuthor(), result.getPrice(), order.getValue());
					resultConver.add(orc);
				}
			}
		}
		bookDAO.saveAll(saveList);
		return new BookSellingResponse(resultConver, sum, RtnCode.SUCCESSFUL.getMessage());
	}

	@Override
	public BookSellingResponse saleRank() {
		List<BookSelling> resultList = bookDAO.findTop5ByOrderBySoldQuantityDesc();
		// 轉換銷售顯示
		List<SearchResultConvert> resultConver = new ArrayList<>();
		for (BookSelling result : resultList) {
			SearchResultConvert sfc = new SearchResultConvert(result.getIsbn(), result.getName(), result.getAuthor(),
					result.getPrice());
			resultConver.add(sfc);
		}
		return new BookSellingResponse(RtnCode.SUCCESSFUL.getMessage(), resultConver);
	}



}
