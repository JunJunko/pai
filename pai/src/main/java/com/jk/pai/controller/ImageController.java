package com.jk.pai.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
@RequestMapping(value = "/image")
public class ImageController {


	@RequestMapping(value = "/getImage")
	@ResponseBody
	public void getImage1(HttpServletResponse response, String imageName) {
		response.setContentType("image/jpeg");

		File file = new File("image/"+imageName);
		InputStream in = null;
		try {
			in = new FileInputStream(file);
			try {
				IOUtils.copy(in, response.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

}
