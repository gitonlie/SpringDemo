package com.service;

import com.entity.Goods;
import com.entity.User;

public interface IUserService {
	public int addUserInfo(User user) throws Exception;
	
	public User queryUserInfo(User user);

}
