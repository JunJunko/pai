package com.jk.pai.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.jk.pai.model.Item;


@Component
public class ExcelUtil {

	@SuppressWarnings("resource")
	public static List<Item> readExecl(String fileName, byte[] buf) {
		boolean isE2007 = false; 
		List<Item> ReList = new ArrayList<Item>();
		 
		
		if (fileName.endsWith("xlsx"))
			isE2007 = true;
		try {
			InputStream input = new ByteArrayInputStream(buf); 
			Workbook wb = null;
			if (isE2007)
				wb = new XSSFWorkbook(input);
			else
				wb = new HSSFWorkbook(input);
			org.apache.poi.ss.usermodel.Sheet sheet = wb.getSheetAt(0);
			int lastRowNum = sheet.getLastRowNum();

			Row row = null;
			Cell cell_item_name = null;
			Cell cell_url = null;
			Cell cell_price = null;
			Cell cell_image_url = null;
			

			for (int i = 1; i <= lastRowNum; i++) {
				Item tableconf = new Item();
				
				row = sheet.getRow(i); 
				cell_item_name = row.getCell(1); 
				tableconf.setItemName(cell_item_name.getStringCellValue().trim());
				
				cell_url = row.getCell(10); 
				tableconf.setUrl(cell_url.getStringCellValue().trim()); 

				
				cell_price = row.getCell(5); 
				tableconf.setPrice(cell_price.getStringCellValue().trim());
				
				cell_image_url = row.getCell(2); 
				tableconf.setImageUrl(cell_image_url.getStringCellValue().trim());
				
				
				ReList.add(tableconf);
				
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return ReList;

	}

}