package com.jk.pai.controller;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jk.pai.model.Item;
import com.jk.pai.service.IGetItemListService;

@RestController
@RequestMapping(value = "/pai")
public class ItemController {
	
	@Autowired
	IGetItemListService getItemListService;
	
	@RequestMapping(value = "/itemlist", method = RequestMethod.GET)
	public void listItem(Writer writer,String channel) {
		 StringBuffer sbHtml = new StringBuffer();
		 try {
//			 sbHtml.append(FileUtils.readFileToString(new ClassPathResource("item.html").getFile()));
			 sbHtml.append(FileUtils.readFileToString(new File("web/item.html")));

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
	
	@RequestMapping(value = "/getlist")
	public List<Item> getList() {
		return getItemListService.getItemList();
	}
}
