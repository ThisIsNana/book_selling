package com.example.book_selling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.example.book_selling.constants.RtnCode;
import com.example.book_selling.entity.BookSelling;
import com.example.book_selling.repository.BookSellingDAO;
import com.example.book_selling.service.ifs.BookSellingService;
import com.example.book_selling.vo.BookSellingResponse;

@SpringBootTest(classes = BookSellingApplication.class)
class BookSellingApplicationTests {

	@Autowired
	private BookSellingService bookService;

	@Autowired
	private BookSellingDAO bookDAO;

	@Test
	public void addBookTest() {
		List<BookSelling> bookList = new ArrayList<>();
		// 正常的
		bookList.add(null);
		bookList.add(new BookSelling("9786263580864", "約會心理學", "OL大獅", 200, "[中文,心靈]", 80));
		bookList.add(new BookSelling("978626358086", "約會心理學", "OL大獅", 200, "[中文,心靈]", 80));
		bookList.add(new BookSelling("9786263580864", "", "OL大獅", 200, "[中文,心靈]", 80));
		bookList.add(new BookSelling("9786263580864", "約會心理學", "", 200, "[中文,心靈]", 80));
		bookList.add(new BookSelling("9786263580864", "約會心理學", "OL大獅", -200, "[中文,心靈]", 80));
		bookList.add(new BookSelling("9786263580864", "約會心理學", "OL大獅", 200, "", 80));
		bookList.add(new BookSelling("9786263580864", "約會心理學", "OL大獅", 200, "[中文,心靈]", -80));
		BookSellingResponse bookResponse = bookService.AddBook(bookList);
		Assert.isTrue(bookResponse.getMessage().equalsIgnoreCase(RtnCode.DATA_ERROR.getMessage()), "出現錯誤!");
	}

//	@Test
//	public void updateBookByIsbnTest() {
//		int result = bookDAO.updateBookByIsbn(200, "[中文, 心靈, 醫療]", 91, "9786263580857");
//		System.out.println(result);
//	}

//	@Test
//	public void findAllByKeywordTest() {
//		List<BookSelling> resultList = bookDAO.findAllByKeyword("哈");
//		for(BookSelling result : resultList) {
//			System.out.println(result);
//		}
//	}

//	@Test
//	public void findAllByKeywordForCustomerTest() {
//		List<Object[]> resultList = bookDAO.SearchAllByKeywordForCustomer("哈");
//		for(Object[] result : resultList) {
//			System.out.println(result);
//		}
//	}

//	@Test
//	public void SearchAllByKeywordForSupplierTest() {
//		List<Object[]> resultList = bookDAO.SearchAllByKeywordForSupplier("哈");
//		for(Object[] result : resultList) {
//			System.out.println(result);
//		}
//	}

	@Test
	public void UpdateBookTest(){
		BookSellingResponse result = bookService.UpdateBook("9786263580856", 120, "英文, 兒童", 100);
		System.out.println(result.getMessage());
	}
	
	
	@Test
	public void OrderBookTest() {
		Map<String, Integer> orderMap = new HashMap<>();
		orderMap.put("9786263580855", 1);
		orderMap.put("9786263580856", 1);
		orderMap.put("9786263580857", 3);
		BookSellingResponse orderResponse = bookService.OrderBook(orderMap);
		System.out.println(orderResponse.getMessage());
	}
	
	//測試一下list會不會加空白
	@Test
	public void test() {
		String st = "a,b,,d,,";
		List<Object> str = Arrays.asList(st.split(","));
		for(Object s:str) {
			System.out.println(s);
		}
	}
	
	
	

	// ===========之前的TEST範例================
//	@Test
//	public void depositTest() {
//		//先創建測試資料
//		Bank oldBank = bankDAO.save(new Bank("acc999","pwd99999@", 2000));
//		//存款--確認帳密相同
//		Bank newBank = new Bank("acc999","pwd99999@", 3000);
//		BankResponse response = bankService.deposit(newBank);
//		//測試金額存入的是否正確.isTrue(條件,false時的錯誤訊息)
//		Bank resultBank = response.getBank();		
//		Assert.isTrue(resultBank.getAmount() == oldBank.getAmount() + newBank.getAmount(), "金額錯誤");
//		//刪除測試資料
//		bankDAO.delete(resultBank);
//		
//	}

}
