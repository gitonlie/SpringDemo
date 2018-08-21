package com.redis;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.entity.Goods;
import com.service.IGoodsService;
import com.thread.ProductProgram1Runnable;
import com.thread.ProductProgram2Runnable;
import com.util.RedisTools;
import com.util.SpringContextHolder;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
/**
 * redis分布式锁实现
 * @author Administrator
 *
 */
public class DistributedLockUtil {
	private static Logger logger = LoggerFactory.getLogger(DistributedLockUtil.class);
	private static JedisPool jedisPool = RedisTools.jedisPool;
	private static AtomicInteger a = new AtomicInteger(0);
    private DistributedLockUtil(){
    }
    
    /**
     * Program_product
     * @param productNum 商品数量  <br/> 
     *  单一商品库初始化
     */
    public static void initGoods(int productNum){
    	String stock_key = "product",listKey = "user_list";
    	Jedis jedis = null;
    	try{
    		jedis = jedisPool.getResource();
    		if(jedis.exists(stock_key)){
    			jedis.del(stock_key);
    		}
    		if(jedis.exists(listKey)){
    			jedis.del(listKey);
    		}
    		jedis.set(stock_key,String.valueOf(productNum));
    	}catch(Exception e){
    		e.printStackTrace();
    	}finally{
    		jedis.close();
    	}
    }
    /**
     * Program_product
     * @param people 参加人数
     * 抢购商品
     */
    public static void initClient(int people){
    	ExecutorService service = Executors.newFixedThreadPool(100);
    	int threadNum = people;
    	for(int i=0;i<threadNum;i++){
    		//service.execute(new ProductProgram1Runnable(i));
    		//service.execute(new ProductProgram2Runnable(i));
    	}
    	service.shutdown();
    	while (true) {
              if (service.isTerminated()) {
                  System.out.println("所有的消费者线程均结束了");    
                  break;   
              }
              try {
                  Thread.sleep(100);
              } catch (Exception e) {
            	  e.printStackTrace();
              }
          }
    }
    /**
     * Program_product
     * 打印抢购结果
     */
    public static void printResult(){
    	Jedis jedis = jedisPool.getResource();
    	Set<String> set = jedis.smembers("user_list");
        int i = 1;
        for (String value : set) {
            System.out.println("第" + i++ + "个抢到商品，"+value + " "); 
        }
        jedis.close();
    }
    
    /**
     * Program_product
     * 尝试获取锁
     * @param seconds 尝试获取锁的时间
     * @param unit	时间单位
     * @param owner 锁拥有者
     * @return
     */
    public static boolean tryLock(int seconds,TimeUnit unit,String owner){
    	long startTime = System.currentTimeMillis();
    	long outTime = unit.toMillis(seconds);
    	Jedis jedis = null;
    	while(System.currentTimeMillis()<(startTime+outTime)){//获取锁的时间   		
        	try{
        		jedis = jedisPool.getResource();
        		if(jedis.setnx("flag",owner)==1){
        			jedis.expire("flag",10);
        			return true;
        		}
        		
        		if(jedis.ttl("flag")==-1){
        			jedis.expire("flag",10);
        		}
        	}catch(Exception e){
        		e.printStackTrace();
        	}finally{
        		jedis.close();
        	}
    	}
		return false;    	
    }
    /**
     * Program_product
     * 根据锁的拥有者释放锁
     * @param owner
     */
    public static void unlock(String owner){

    	Jedis jedis = null;
        	try{
        		jedis = jedisPool.getResource();
        		if(owner.equals(jedis.get("flag"))){
        			jedis.del("flag");
        		}
        	}catch(Exception e){
        		e.printStackTrace();
        	}finally{
        		jedis.close();
        	}
    }
    
    /**
     * 多商品goods
     * 同步数据到redis
     * 初始化商品列表
     */
    public static void initInventory(){
    	logger.info("初始化数据");
    	String dataKey = "goods";
    	IGoodsService service = SpringContextHolder.getBean("goodsService");
    	List<Goods> list = service.queryGoodsList();
    	Map<String,String>  map = new HashMap<String, String>();
    	for(Goods goods:list){
    		map.put("lock:"+goods.getId(),String.valueOf(goods.getStock()));
    	}
    	//获取连接
    	Jedis jedis = null;
    	try{
    		jedis = jedisPool.getResource();
    		if(jedis.exists(dataKey)){
    			jedis.del(dataKey);
    		}
    		jedis.hmset(dataKey, map);
    	}catch(Exception e){
    		e.printStackTrace();
    	}finally{
    		jedis.close();
    	}
    }
    
    /**
     * 多商品goods
     * 尝试获得锁
     * @param productKey 产品KEY
     * @param owner		   拥有者
     * @param seconds    获取锁超时时间
     * @param timeUnit	   时间单位
     * @return
     */
    public static boolean Lock(String productKey,String owner,int seconds,TimeUnit timeUnit){
    	Jedis jedis = null;
    	try{
    		jedis = jedisPool.getResource();
    		long timeOut = System.currentTimeMillis() + timeUnit.toMillis(seconds);//超时获取锁 
    		while(System.currentTimeMillis()<timeOut){//轮询获取锁
    			if(jedis.setnx(productKey,owner)==1){//获取锁成功
    				jedis.expire(productKey,10);
    				logger.info("[{}]已获得[{}]的锁",owner,productKey);
    				return true;
        		}
    			//过期时间未设置的需重置避免死锁
    			if(jedis.ttl(productKey)==-1){
    				jedis.expire(productKey,10);
    			}   				
    				Thread.sleep(10);//延迟操作
    		} 		
    	}catch(Exception e){
    		e.printStackTrace();
    	}finally{
    		jedis.close();
    	}
    	return false;
    }
    
    
    /**
     * 多商品goods
     * @param productKey 商品key
     * @param owner      拥有者
     */
    public static void unlock(String productKey,String owner){
    	Jedis jedis = null;
    	try{
    		jedis = jedisPool.getResource();
    		if(owner.equals(jedis.get(productKey))){
    			jedis.del(productKey);
    			logger.info("[{}]释放锁[{}]",owner,productKey);
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}finally{
    		jedis.close();
    	}
    }
}
