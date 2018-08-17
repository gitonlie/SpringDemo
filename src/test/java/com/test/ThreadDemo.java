package com.test;

public class ThreadDemo {
	public static void main(String[] args) {
		for(int i=0;i<3;i++){
			ThreadDemoSon t = new ThreadDemoSon();
			t.start();
		}
	}
}

class ThreadDemoSon extends Thread{

	@Override
	public void run() {
		M t = new M();
		t.done();
	}		
}

class M{
	public void done(){
		synchronized (M.class) {
			System.out.println("test开始");
			try {
				Thread.sleep(1000*3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("test结束");
		}
	}
}