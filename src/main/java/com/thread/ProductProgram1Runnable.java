package com.thread;

import java.util.List;

import com.util.RedisTools;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

/**
 * Program_product_1(乐观锁)<br/>
 * <h1>Redis乐观锁实现</h1>
 * 1.multi，开启Redis的事务，置客户端为事务态。<br/>
 * 2.exec，提交事务，执行从multi到此命令前的命令队列，置客户端为非事务态。 <br/>
 * 3.discard，取消事务，置客户端为非事务态。 <br/>
 * 4.watch,监视键值对，作用是如果事务提交exec时发现监视的键值对发生变化，事务将被取消。<br/>
 * @author Administrator
 *	
 */
public class ProductProgram1Runnable implements Runnable{
	private String clientName;
	public ProductProgram1Runnable(int num){
		clientName = "顾客["+num+"]";
	}
	@Override
	public void run() {
		Jedis jedis=null;
		while(true)
		{
			try{
				System.out.println(clientName+"开始抢购");
				jedis = RedisTools.jedisPool.getResource();
				//监视商品键值对，作用时如果事务提交exec时发现监视的键值对发生变化，事务将被取消
				String prokey="product";
				jedis.watch(prokey);
				int count = Integer.valueOf(jedis.get(prokey));
				if(count>0){
					//开启redis事物
					Transaction transaction = jedis.multi();
					//商品数量减一
					transaction.set(prokey,String.valueOf(count-1));
					//提交事务(乐观锁：提交事务的时候才会去检查key有没有被修改)
					List<Object> result = transaction.exec();
					if (result == null || result.isEmpty()) {
	                    System.out.println("很抱歉，" + clientName + "没有抢到商品");// 可能是watch-key被外部修改，或者是数据操作被驳回
	                }else {
	                    jedis.sadd("user_list", clientName);//抢到商品的话记录一下
	                    System.out.println("恭喜，" + clientName + "抢到商品");  
	                    break; 
	                }
				}else{
					 System.out.println("很抱歉，库存为0，" + clientName + "没有抢到商品");  
                     break; 
				}
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				jedis.unwatch();
				jedis.close();
			}
		}
	}
}
