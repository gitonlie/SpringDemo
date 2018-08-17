package com.test;

public class A {
	
	static{
		System.out.println("A中静态代码块");
	}
	
	{
		System.out.println("A中的构造块");
	}
	
	public A(){
		System.out.println("A的构造方法");
	}
	
	public void doSomething(){
		System.out.println("A的普通方法");
	}
}
