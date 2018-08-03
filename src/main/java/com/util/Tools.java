package com.util;

import com.entity.Ticket;

public class Tools {
	
	private static Ticket ticket = null;
	
	public synchronized static int getTicket(){
		if(ticket==null){
			ticket = new Ticket(10000, "电影票");
		}
		int i = ticket.getNumber();
		i--;
		ticket.setNumber(i);
		return ticket.getNumber();
	}
	
	
}
