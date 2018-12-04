package com.jk.pai.controller;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jk.pai.model.Item;
import com.jk.pai.service.ISelectedProductsService;

@SpringBootApplication
@RequestMapping(value = "/import")
public class ImportProductsController {
	
	@Autowired
	ISelectedProductsService selectedProductsService;

	@GetMapping("/upload")
	public void upload(Writer writer) {
		StringBuffer sbHtml = new StringBuffer();
		try {
			// sbHtml.append(IOUtils.readLines(new
			// ClassPathResource("index.html").getInputStream()));
			sbHtml.append(FileUtils.readFileToString(new File("web/upload.html")));
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

	@PostMapping("/upload")
	@ResponseBody
	public String upload(@RequestParam("file") MultipartFile file) {
		if (file.isEmpty()) {
			return "上传失败，请选择文件";
		}
		String fileName = file.getOriginalFilename();
		File dest = new File(fileName);
		
		try {
			List<Item> itemList = selectedProductsService.AnalysisExcle(fileName, file.getBytes());
			selectedProductsService.ImportProduct2DataBase(itemList);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			file.transferTo(dest);
			return "上传成功";
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "上传失败！";
	}
}
