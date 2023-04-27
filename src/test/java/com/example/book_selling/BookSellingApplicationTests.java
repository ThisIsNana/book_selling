package com.example.book_selling;

import java.util.ArrayList;
import java.util.List;

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
		//正常的
		bookList.add(null);
		bookList.add(new BookSelling("9786263580864","約會心理學","OL大獅",200,"[中文,心靈]",80));
		bookList.add(new BookSelling("978626358086","約會心理學","OL大獅",200,"[中文,心靈]",80));
		bookList.add(new BookSelling("9786263580864","","OL大獅",200,"[中文,心靈]",80));
		bookList.add(new BookSelling("9786263580864","約會心理學","",200,"[中文,心靈]",80));
		bookList.add(new BookSelling("9786263580864","約會心理學","OL大獅",-200,"[中文,心靈]",80));
		bookList.add(new BookSelling("9786263580864","約會心理學","OL大獅",200,"",80));
		bookList.add(new BookSelling("9786263580864","約會心理學","OL大獅",200,"[中文,心靈]",-80));
		BookSellingResponse bookResponse = bookService.AddBook(bookList);
		Assert.isTrue(bookResponse.getMessage().equalsIgnoreCase(RtnCode.DATA_ERROR.getMessage()),"出現錯誤!");
	}
	
	@Test
	public void updateBookByIsbnTest() {
		int result = bookDAO.updateBookByIsbn(200, "[中文, 心靈, 醫療]", 91, "9786263580857");
		System.out.println(result);
	}
	
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
