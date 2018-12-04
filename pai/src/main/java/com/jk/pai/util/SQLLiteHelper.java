package com.jk.pai.util;

public class SQLLiteHelper extends SqlHelper {

	public SQLLiteHelper(String userName, String passWord) {
		super();
		super.url = getUrl();
		super.password = passWord;
		super.userName = userName;
	}

	public String getUrl() {

		return "jdbc:sqlite:db/pai.db";

	}

}
