package com.asd.cms.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.asd.cms.dao.UserWiseDao;
import com.asd.cms.model.TUser;

public class UserWiseReportServiceImpl implements UserWiseReportService {
	
	@Autowired
	private UserWiseDao userWiseDao;

	@Transactional
	public List<TUser> getUserList(){
		
		return userWiseDao.getUserList();
	}
}
