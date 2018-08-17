package com.thread;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.util.Constant;
import com.util.DistributedLockUtil;
import com.util.RedisTools;

public class GoodsCallable implements Callable<Object> {
	
	private Logger logger = LoggerFactory.getLogger(GoodsCallable.class);
	private String client;	
	public GoodsCallable(int id) {
		super();
		this.client = "用户"+id;
	}

	@Override
	public Object call(){
		String pid = plusStock();
		Jedis jedis = null;
		String field = Constant.LockField(pid);
		if(DistributedLockUtil.Lock(pid,client,5,TimeUnit.SECONDS)){
			try{
				jedis = RedisTools.jedisPool.getResource();
				
				if(jedis.hexists(Constant.GOODS,field)){
					String c = jedis.hget(Constant.GOODS,field);
				}
			}catch(Exception e){
				
			}finally{
				
			}
		}else{
			logger.info("{}未取得锁{}",client,pid);
		}
		return pid;
	}
	
	protected String plusStock(){
		return String.valueOf(((new Random()).nextInt(4))+1);
	}

}
