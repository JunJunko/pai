package com.jk.pai.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jk.pai.model.Item;
import com.jk.pai.service.ISelectedProductsService;
import com.jk.pai.util.ExcelUtil;
import com.jk.pai.util.SQLLiteHelper;

@Service
public class SelectedProductsServiceImp implements ISelectedProductsService {

	@Autowired
	ExcelUtil excel;

	@Override
	public List<Item> AnalysisExcle(String FilePath, byte[] buf) {
		// TODO Auto-generated method stub
 
		return ExcelUtil.readExecl(FilePath, buf);
	}

	@Override
	public boolean ImportProduct2DataBase(List<Item> itemList) {
		// TODO Auto-generated method stub
		return new SQLLiteHelper(null, null).insertArpStandardList(itemList,
				"insert into item_list (item_name, url, price, image_url) values (?,?,?,?)");

	}

}
