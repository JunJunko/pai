package com.jk.pai.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jk.pai.model.Item;


@Service
public interface ISelectedProductsService {
	
	
	public boolean ImportProduct2DataBase(List<Item> itemList);

	public List<Item> AnalysisExcle(String FilePath, byte[] buf);

}
