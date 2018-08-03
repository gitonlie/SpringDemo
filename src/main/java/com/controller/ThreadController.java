package com.controller;

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
}
