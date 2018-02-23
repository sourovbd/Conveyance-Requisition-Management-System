package com.asd.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.asd.cms.model.TUser;

@Service
public interface UserWiseReportService {

	public List<TUser> getUserList();
}
