package com.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Ticket implements Serializable {
	
	public int number;
	
	private String name;

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Ticket(int number, String name) {
		super();
		this.number = number;
		this.name = name;
	}

	public Ticket(int number) {
		super();
		this.number = number;
	}

	public Ticket() {
		super();
	}
	
	
}	
