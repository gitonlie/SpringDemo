package com.thread;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

public class MyCallable implements Callable<String> {
	
	private  AtomicInteger i;
	public AtomicInteger getI() {
		return i;
	}
	public void setI(AtomicInteger i) {
		this.i = i;
	}
	

	@Override
	public String call() throws Exception {
		StringBuilder s = new StringBuilder();
		while(true){
			i.incrementAndGet();
			s.append(new Random().nextInt(10));
			if(i.get()>1000*3){
				break;
			}
		}
		return s.toString();
	}

}
