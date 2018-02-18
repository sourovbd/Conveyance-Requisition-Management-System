package com.asd.cms.service;

import com.asd.cms.model.TUser;

public interface UserService {

	public TUser getUserByLoginID(String LoginId);
	public TUser getUserByEmployeeID(String EmployeeId);
}
