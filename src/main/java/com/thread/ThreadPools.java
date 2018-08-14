package com.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ThreadPools {
	
	/**
	 * 创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程
	 * @return
	 */
	public static ExecutorService getNewCachedThreadPool(){
		return Executors.newCachedThreadPool();		
	}
	
	/**
	 * 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待
	 * @param nThreads
	 * @return
	 */
	public static ExecutorService getNewFixedThreadPool(int nThreads){
		return Executors.newFixedThreadPool(3);		
	}
	
	/**
	 * 创建一个定长线程池，支持定时及周期性任务执行
	 * @return
	 */
	public static ScheduledExecutorService getNewScheduledThreadPool(){
		return Executors.newScheduledThreadPool(3);		
	}
	
	/**
	 * 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行
	 * @return
	 */
	public static ExecutorService getNewSingleThreadExecutor(){
		return Executors.newSingleThreadExecutor();		
	}
}
