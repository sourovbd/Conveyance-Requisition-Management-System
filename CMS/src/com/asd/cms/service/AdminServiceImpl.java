package com.asd.cms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.asd.cms.dao.AdminDao;
import com.asd.cms.model.TAdminView;
import com.asd.cms.model.TExpense;
import com.asd.cms.model.TRequisition;
import com.asd.cms.model.TUser;

public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminDao adminDao;

	@Override
	public List<TExpense> getExpenseList(String empID) {
		
		return adminDao.getExpenseList(empID);
	}
	
	@Override
	public List<String> getUniqueEmpId() {
		
		return adminDao.getUniqueEmpId();
	
	}

	@Override
	public List<TExpense> getDetailsInfo(long id) {
		
		return adminDao.getDetailsInfo(id);
	}

	@Override
	public boolean approveStatusDate(Long id) {

		return adminDao.approveStatusDate(id);
	}

	@Override
	public boolean rejectStatusDate(Long id) {
		
		return adminDao.rejectStatusDate(id);
	}

	@Override
	public List<TUser> getHrList() {
		
		return adminDao.getHrList();
	}

	@Override
	public List<TExpense> getConveyanceViewDetails(String dateFrom,String dateTo) {
		
		return adminDao.getConveyanceViewDetails(dateFrom, dateTo);
	}

	@Override
	public List<String> getUniqueEmpIdReq() {

		return adminDao.getUniqueEmpIdReq();
	}

	@Override
	public List<TRequisition> getRequisitionList(String empID) {

		return adminDao.getRequisitionList(empID);
	}

	@Override
	public List<TRequisition> getDetailsInfoReq(long id) {

		return adminDao.getDetailsInfoReq(id);
	}

	@Override
	public boolean approveStatusDateReq(Long id) {

		return adminDao.approveStatusDateReq(id);
	}

	@Override
	public boolean rejectStatusDateReq(Long id) {

		return adminDao.rejectStatusDateReq(id);
	}

	@Override
	public List<TUser> getHrListReq() {
		
		return adminDao.getHrListReq();
	}

	@Override
	public List<TRequisition> getEntryViewDetailsReq(String reqDateFrom,
			String reqDateTo) {
		
		return adminDao.getEntryViewDetailsReq(reqDateFrom, reqDateTo);
	}

}
