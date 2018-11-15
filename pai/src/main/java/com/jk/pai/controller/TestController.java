package com.jk.pai.controller;

import java.io.IOException;
import java.io.Writer;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@SpringBootApplication
// 表示该controller类下所有的方法都公用的一级上下文根
@RequestMapping(value = "/pai")

public class TestController {

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public void getUserByGet( Writer writer) {
		 StringBuffer sbHtml = new StringBuffer();
		sbHtml.append("<!doctype html><html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
        sbHtml.append("<title>支付宝即时到账交易接口</title></head><body>"+ "afafasfaf" +"</body></html>");
        try {
			writer.write(sbHtml.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}

}
