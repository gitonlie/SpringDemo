package com.controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.entity.User;
import com.service.IUserService;

@Controller
public class ThreadController extends BaseController{
	@Autowired
	private IUserService service;
	
	@RequestMapping(value="/add")
	@ResponseBody
	public String testAddUser() throws Exception{
		User user = new User();
		user.setName("good");
		service.addUserInfo(user);
		return "添加成功";
	}
	
	@RequestMapping(value="/session")
	@ResponseBody
	public String testSession(HttpServletRequest request,HttpServletResponse response){
		String msg = "";
		HttpSession session = request.getSession();
		if(session.getAttribute("port")==null){
			session.setAttribute("port", request.getRemotePort());
		}
		msg=request.getRequestedSessionId()+":"+request.getRemotePort()+","+session.getAttribute("port");
		return msg;
	}
}
