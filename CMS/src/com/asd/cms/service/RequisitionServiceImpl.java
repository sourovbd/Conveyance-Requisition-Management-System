package com.asd.cms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asd.cms.model.TRequisition;
import com.asd.cms.dao.RequisitionDao;

@Service
public class RequisitionServiceImpl implements RequisitionService {

	@Autowired
	private RequisitionDao requisitionDao;

	@Override
	public boolean insert(TRequisition requisition) {
		
		return requisitionDao.insert(requisition);
	}

	@Override
	public List<TRequisition> getEmployeeInfo(String loginId) {
		
		return requisitionDao.getEmployeeInfo(loginId);
	}

	@Override
	public boolean updateStatusDate(long id) {
		
		return requisitionDao.updateStatusDate(id);
	}

	@Override
	public boolean delete(TRequisition requisition) {
		
		return requisitionDao.delete(requisition);
	}

	@Override
	public List<TRequisition> getRequisitionViewDetails(String reqDateFrom,
			String reqDateTo, String loginId) {
		
		return requisitionDao.getRequisitionViewDetails(reqDateFrom, reqDateTo, loginId);
	}
	
}
