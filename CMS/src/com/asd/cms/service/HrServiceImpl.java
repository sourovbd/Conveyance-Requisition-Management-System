package com.asd.cms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.asd.cms.dao.HrDao;
import com.asd.cms.model.TExpense;
import com.asd.cms.model.TRequisition;
import com.asd.cms.model.TUser;

public class HrServiceImpl implements HrService {

	@Autowired
	private HrDao hrDao;

	@Override
	public List<String> getUniqueEmpId() {
		
		return hrDao.getUniqueEmpId();
	}

	@Override
	public List<TExpense> getExpenseList(String empId) {
		
		return hrDao.getExpenseList(empId);
	}

	@Override
	public boolean approveStatusDate(String id) {
		
		return hrDao.approveStatusDate(id);
		
	}

	@Override
	public boolean SaveOrUpdate(TUser user) {
		
		return hrDao.SaveOrUpdate(user);
	}

	@Override
	public List<TUser> viewUser() {
		
		return hrDao.viewUser();
	}

	@Override
	public TUser getUser(String id) {
		
		return  hrDao.getUser(id);
	}
	
	@Override
	public boolean deleteUser(String id) {
		
		return hrDao.deleteUser(id);
	}

	@Override
	public boolean activateUser(String id) {
		
		return hrDao.activateUser(id);
	}

	@Override
	public boolean deactivateUser(String id) {

		return hrDao.deactivateUser(id);
	}

	@Override
	public List<TExpense> getDetailsInfo(long id) {
		
		return hrDao.getDetailsInfo(id);
	}

	@Override
	public TUser getEmployee(String empId) {
		
		return hrDao.getEmployee(empId);
	}

	@Override
	public TUser getEmployee(Long empId) {
		
		return hrDao.getEmployee(empId);
	}

	@Override
	public List<TExpense> getConveyanceViewDetails(String dateFrom,String dateTo) {
		
		return hrDao.getConveyanceViewDetails(dateFrom, dateTo);
	}

	@Override
	public List<String> getUniqueEmpIdReq() {
		
		return hrDao.getUniqueEmpIdReq();
	}

	@Override
	public List<TRequisition> getRequisitionList(String empId) {
		
		return hrDao.getRequisitionList(empId);
	}

	@Override
	public List<TRequisition> getDetailsInfoReq(long id) {
		
		return hrDao.getDetailsInfoReq(id);
	}

	@Override
	public boolean approveStatusDateReq(String id) {
		
		return hrDao.approveStatusDateReq(id);
	}

	@Override
	public List<TRequisition> getRequisitionViewDetails(String dateFrom,
			String dateTo) {
		
		return hrDao.getRequisitionViewDetails(dateFrom, dateTo);
	}
}
