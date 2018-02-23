package com.asd.cms.dao;

import com.asd.cms.model.TUser;

public interface CustomUserDetailsDao {

	public TUser getUserByLoginID(String loginID);
}
