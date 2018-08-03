package com.thread;

import com.sun.swing.internal.plaf.synth.resources.synth;

/**
 * Volatile原子性验证
 * @author Administrator
 *
 */
public class VolatileTest {
	
	public volatile int a = 0;
	public int b = 0;
	/**
	 * 非原子性操作
	 */
	public void increaseByself(){
		a++;
	}
	
	public synchronized void increaseByself2(){
		b++;
	}
	
	public static void main(String[] args) {
		final VolatileTest v = new VolatileTest();
		for(int i=0;i<10;i++){
			new Thread(){
				@Override
				public void run() {
					  for(int j=0;j<1000;j++)
	                        v.increaseByself();
					  		//v.increaseByself2();
	                };
			}.start();
		}
		
		while(Thread.activeCount()>1){
			Thread.yield();
			System.out.println("线程数:"+Thread.activeCount());
		}
		System.out.println("最终结果:"+v.a+","+v.b);
	}
}
