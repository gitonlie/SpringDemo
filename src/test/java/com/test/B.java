package com.test;

public class B extends A{
	static{
		//只会被执行一次(第一次加载此类时执行,比如说用Class.forName("")加载类时就会执行static  block)，静态块优先于构造块执行。 
		System.out.println("B中静态代码块");
	}
	
	{
		//构造块在创建对象时会被调用，每次创建对象时都会被调用，并且优先于类构造函数执行。 构造块中定义的变量是局部变量
		System.out.println("B中的构造块");
	}
	
	public B(){
		//系统默认调用父类super()
		//super();
		System.out.println("B的构造方法");		
	}
	
	@Override
	public void doSomething(){
		System.out.println("B的普通方法");
	}
}
