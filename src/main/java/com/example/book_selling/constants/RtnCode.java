package com.example.book_selling.constants;

public enum RtnCode {
	// 存放常數及回覆constants = 常數，Rtn = return
	SUCCESSFUL("200", "完成!"), 
	CANNOT_EMTPY("400", "欄位不可空白"), // 前面名字可自己定義
	DATA_ERROR("400", "輸入格式有誤"), 
	UNAUTHORIZED("401", "尚未輸入身分!"), 
	ALREADY_AUTHORIZED("401", "帳號已驗證，請直接登入"),
	NOT_FOUND("404", "查無資料"), 
	ALREADY_PRESENT("409", "重複新增");

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
