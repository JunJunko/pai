package com.jk.pai.controller;

import java.io.File;
import java.io.IOException;
import java.io.Writer;

import org.apache.commons.io.FileUtils;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@SpringBootApplication
// 表示该controller类下所有的方法都公用的一级上下文根
@RequestMapping(value = "/pai")

public class TestController {

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public void getUserByGet( Writer writer) {
		StringBuffer sbHtml = new StringBuffer();
		 try {
			 sbHtml.append(FileUtils.readFileToString(new File("web/test2.html")));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
       try {
			writer.write(sbHtml.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}

}
