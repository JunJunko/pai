package com.jk.pai.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
@RequestMapping(value = "/image")
public class ImageController {

    @RequestMapping(value = "/logo")
    @ResponseBody
    public void getLogo(HttpServletResponse response) {
        response.setContentType("image/jpeg");

        File file = null;
		try {
			file = new ClassPathResource("static/image/logo-fanli.index-v2.png").getFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
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
    
    
    
    @RequestMapping(value = "/image1")
    @ResponseBody
    public void getImage1(HttpServletResponse response) {
        response.setContentType("image/jpeg");

        File file = null;
		try {
			file = new ClassPathResource("static/image/image1.jpg").getFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
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
