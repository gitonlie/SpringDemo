package com.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.GoodsMapper;
import com.entity.Goods;
import com.service.IGoodsService;
@Service
public class GoodsService implements IGoodsService {
	@Autowired
	private GoodsMapper goodsDao;
	
	@Override
	public int updateGoods(Goods goods) {
		return goodsDao.updateGoods(goods);
	}

	@Override
	public List<Goods> queryGoodsList() {
		return goodsDao.queryGoodsList();
	}

}
