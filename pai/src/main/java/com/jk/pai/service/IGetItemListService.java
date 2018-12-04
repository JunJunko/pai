package com.jk.pai.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jk.pai.model.Item;


@Service
public interface IGetItemListService {
	
	List<Item> getItemList();
	

}
