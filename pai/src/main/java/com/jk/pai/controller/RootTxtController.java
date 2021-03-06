package com.jk.pai.controller;

import java.io.File;
import java.io.IOException;
import java.io.Writer;

import org.apache.commons.io.FileUtils;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@SpringBootApplication
@RequestMapping(value = "/pai")
public class RootTxtController {
	
	@RequestMapping(value = "root.txt", method = RequestMethod.GET)
	public void listItem(Writer writer,String channel) {
		 StringBuffer sbHtml = new StringBuffer();
		 try {
//			 sbHtml.append(IOUtils.readLines(new ClassPathResource("index.html").getInputStream()));
			 sbHtml.append(FileUtils.readFileToString(new File("root.txt")));
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
