package com.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.UserMapper;
import com.entity.User;
import com.service.IUserService;
@Service
public class UserService implements IUserService {
	
	@Autowired
	private UserMapper userdao;
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public int addUserInfo(User user) throws Exception {
		userdao.insertUserInfo(user);
		if(user!=null){
			throw new Exception();
		}
		userdao.insertUserInfo(user);
		return 1;
	}

}
