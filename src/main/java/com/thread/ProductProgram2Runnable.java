package com.thread;
import java.util.concurrent.TimeUnit;
import com.util.DistributedLockUtil;
import com.util.RedisTools;
import redis.clients.jedis.Jedis;

/**
 * Program_product_2(悲观锁)<br>
 * <h1>Redis悲观锁实现</h1>
 * 首先在单位TimeUnit时间段内获取锁，获取成功的进行库存减一操作<br>
 * 然后释放锁，未取得锁的直接丢弃<br>
 * @author Administrator
 *	
 */
public class ProductProgram2Runnable implements Runnable{
	private final String OK = "OK" ; 
	private String clientName;
	public ProductProgram2Runnable(int num){
		clientName = "顾客["+num+"]";
	}
	@Override
	public void run() {
		Jedis jedis=null;
		if(DistributedLockUtil.tryLock(5,TimeUnit.SECONDS,clientName)){
			try{				
				System.out.println(clientName+"开始抢购");
				jedis = RedisTools.jedisPool.getResource();
				String prokey="product";
				int count = Integer.valueOf(jedis.get(prokey));
				if(count>0){
					String reply = jedis.set(prokey,String.valueOf(count-1));
					if (!OK.equals(reply)) {
	                    System.out.println("很抱歉，" + clientName + "没有抢到商品");
	                }else {
	                    jedis.sadd("user_list", clientName);//抢到商品的话记录一下
	                    System.out.println("恭喜，" + clientName + "抢到商品");
	                }
				}else{
					 System.out.println("很抱歉，库存为0，" + clientName + "没有抢到商品");
				}
				DistributedLockUtil.unlock(clientName);
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				jedis.close();
			}
		}else{
			System.out.println(clientName+"未获得锁");
		}
	}
}
