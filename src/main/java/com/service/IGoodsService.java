package com.service;

import java.util.List;

import com.entity.Goods;

public interface IGoodsService {
	
	public int updateGoods(Goods goods);
	
	public List<Goods> queryGoodsList();
}
