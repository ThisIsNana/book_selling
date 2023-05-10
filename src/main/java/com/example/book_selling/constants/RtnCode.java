package com.example.book_selling.constants;

public enum RtnCode {
	// 存放常數及回覆constants = 常數，Rtn = return
	SUCCESSFUL("200", "完成!"), 
	CANNOT_EMTPY("400", "欄位不可空白"), // 前面名字可自己定義
	ISBN_CANNOT_EMTPY("400", "ISBN為必填"), 
	IDENTIFY_CANNOT_EMTPY("400", "身分攔為必填!1為消費者，0為廠商!"), //
	DATA_ERROR("400", "輸入格式有誤"), 
	INT_ERROR("400", "輸入數字或數量有誤!"), 
	NOT_IN_STOCK("400", "庫存不足，請確認訂單!"), 
	UNAUTHORIZED("401", "尚未輸入身分!"), 
	NOT_FOUND("404", "無相符資料"), 
	ALREADY_PRESENT("409", "重複新增"),
	NO_CHANGE("409", "資料未修改");

	private String code;
	private String message;

	private RtnCode(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
