package com.jk.pai.controller;

import java.io.Writer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jk.pai.service.ISelectedProductsService;

@SpringBootApplication
// 表示该controller类下所有的方法都公用的一级上下文根
@RequestMapping(value = "/pai")

public class TestController {
	
	@Autowired
	ISelectedProductsService selectProductsService;

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public void getUserByGet( Writer writer) {
		
//		System.out.println(selectProductsService.AnalysisExcle("D:/tmp/test-2018-12-04.xls"));
	}

}
