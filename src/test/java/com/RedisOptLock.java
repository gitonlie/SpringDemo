package com;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.redis.DistributedLockUtil;
import com.thread.GoodsCallable;

public class RedisOptLock {
	public static void main(String[] args) {
		DistributedLockUtil.initGoods(500);
		DistributedLockUtil.initClient(1000);
		DistributedLockUtil.printResult();
		
	}
	
	public static String test(){
		try{
			System.out.println("语句返回之前");
			return "语句返回";
		}catch(Exception e){
			
		}finally{
			System.out.println("执行了");
		}
		return null;
	}
	
	public static void manyGoods() throws InterruptedException, ExecutionException{
		List<Future<Object>> futureList = new ArrayList<Future<Object>>();
		DistributedLockUtil.initInventory();
		ExecutorService service = Executors.newFixedThreadPool(100);
		for (int i = 0; i <30000; i++) {
			Future<Object> future = service.submit(new GoodsCallable(i));
			futureList.add(future);
		}
		List<Object> objList = new ArrayList<Object>();
		for(int i=0;i<futureList.size();i++){
			Future<Object> future = futureList.get(i);
			Object obj = future.get();
			objList.add(obj);
		}
		service.shutdown();
	}
}
