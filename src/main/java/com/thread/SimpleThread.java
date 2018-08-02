package com.thread;

import java.util.concurrent.atomic.AtomicInteger;

import com.util.Tools;

public class SimpleThread implements Runnable{
	
	private AtomicInteger currentCalc = new AtomicInteger(0);
	
	
	@Override
	public void run() {
		while(true){
			int i=Tools.getTicket();
			if(i<0){
				System.out.println(Thread.currentThread()+":结束");
				break;
			}
			System.out.println(Thread.currentThread()+"执行次数=>"+currentCalc.getAndIncrement()+"次:当前票数=>"+i);
			
		}
	}


	public AtomicInteger getCurrentCalc() {
		return currentCalc;
	}


	public void setCurrentCalc(AtomicInteger currentCalc) {
		this.currentCalc = currentCalc;
	}
	
}
