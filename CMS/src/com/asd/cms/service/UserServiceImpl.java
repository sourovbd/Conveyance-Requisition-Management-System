package com.asd.cms.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.asd.cms.dao.UserDao;
import com.asd.cms.model.TUser;


public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	@Override
	public TUser getUserByLoginID(String LoginID) {
		
		return userDao.getUserByLoginID(LoginID);
	}
	@Override
	public TUser getUserByEmployeeID(String EmployeeId) {
	
		return userDao.getUserByEmployeeID(EmployeeId);
	}

}
