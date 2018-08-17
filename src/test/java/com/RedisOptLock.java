package com;

import com.util.DistributedLockUtil;

public class RedisOptLock {
	public static void main(String[] args) {
		/*DistributedLockUtil.initGoods(500);
		DistributedLockUtil.initClient(1000);
		DistributedLockUtil.printResult();*/
		System.out.println(test());
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
}
