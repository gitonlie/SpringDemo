package com.dao;
import java.util.List;
import com.entity.Goods;
public interface GoodsMapper {
	
	public List<Goods> queryGoodsList();
	
	public int batchUpdate(List<Goods> list);
	
	public int updateGoods(Goods goods);
}
