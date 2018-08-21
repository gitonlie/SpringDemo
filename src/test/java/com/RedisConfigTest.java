package com;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import redis.clients.jedis.Jedis;

import com.redis.DistributedLockUtil;
import com.thread.GoodsCallable;
import com.util.RedisTools;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
@Rollback(value=false)
@Transactional(transactionManager="transactionManager")
public class RedisConfigTest {

	@Test
	public void test() throws Exception{
		DistributedLockUtil.initInventory();
		ExecutorService service = Executors.newFixedThreadPool(10);
		List<FutureTask<Object>> list = new ArrayList<FutureTask<Object>>();
		for(int i=0;i<30000;i++){
			FutureTask<Object> task = new FutureTask<Object>(new GoodsCallable(i));
			list.add(task);
			service.submit(task);
		}
	
		for(FutureTask<Object> futureTask:list){
			String client = (String) futureTask.get();
		}
		
		service.shutdown();
		while(service.isTerminated()){
			break;
		}
		
	}
}
