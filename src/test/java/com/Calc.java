package com;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicInteger;

import redis.clients.jedis.Jedis;

import com.redis.DistributedLockUtil;
import com.thread.MyCallable;
import com.util.RedisTools;
/**
 * java.util.concurrent - Java并发工具包
 * @author Administrator
 *
 */
public class Calc {
	public static void Thread() throws Exception{ //main(String[] args) throws Exception {
//		Thread t1 = new Thread(new SimpleThread());
//		t1.setName("线程1");
//		Thread t2 = new Thread(new SimpleThread());
//		t2.setName("线程2");
//		t1.start();
//		t2.start();
//		Thread.sleep(60*1000);
		 AtomicInteger i = new AtomicInteger(0);
		 ExecutorService service = Executors.newFixedThreadPool(1);
		 MyCallable call = new MyCallable();
		 call.setI(i);
		 FutureTask<String> futureTask = new FutureTask<String>(call);
		 service.execute(futureTask);
		 String s = futureTask.get();
		 System.out.println(s);
		 
	}
	
	public static void main(String[] args) {
		Long i=0L;
		if(i==0L){
			System.out.println("ok");
		}
	}
}
