package com.dao;

import com.entity.User;

public interface UserMapper {
	
	public int insertUserInfo(User user);
	
	public User queryUser(User user);
}
