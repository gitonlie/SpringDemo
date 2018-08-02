package com;
import com.thread.SimpleThread;
/**
 * java.util.concurrent - Java并发工具包
 * @author Administrator
 *
 */
public class Calc {
	public static void main(String[] args) throws Exception {
		Thread t1 = new Thread(new SimpleThread());
		t1.setName("线程1");
		Thread t2 = new Thread(new SimpleThread());
		t2.setName("线程2");
		t1.start();
		t2.start();
		Thread.sleep(60*1000);
	}
}
