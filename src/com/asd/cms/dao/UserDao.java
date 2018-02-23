package com.asd.cms.dao;

import com.asd.cms.model.TUser;

public interface UserDao {

	public TUser getUserByLoginID(String LoginID);
	public TUser getUserByEmployeeID(String EmployeeId);
}
