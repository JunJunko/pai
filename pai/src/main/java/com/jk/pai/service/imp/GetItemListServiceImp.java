package com.jk.pai.service.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.jk.pai.model.Item;
import com.jk.pai.service.IGetItemListService;
import com.jk.pai.util.SQLLiteHelper;

@Service
public class GetItemListServiceImp implements IGetItemListService {

	@Override
	public List<Item> getItemList() {
		// TODO Auto-generated method stub
		List<Item> itemList = new ArrayList<Item>();
		String[] str = new String[] {};
		SQLLiteHelper sQLLiteHelper = new SQLLiteHelper("", "");
		try (ResultSet rs = sQLLiteHelper.executeQuery("select * from item_list", str)) {
			while (rs.next()) {
				Item item = new Item();
				item.setUrl(rs.getString("url"));
				item.setItemName(rs.getString("item_name"));
				item.setPrice(rs.getString("price"));
				item.setImageUrl(rs.getString("image_url"));
				itemList.add(item);
			}
		}

		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return itemList;
	}



}