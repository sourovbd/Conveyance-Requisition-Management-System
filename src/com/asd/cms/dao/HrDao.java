package com.asd.cms.dao;

import java.util.List;

import com.asd.cms.model.TExpense;
import com.asd.cms.model.TRequisition;
import com.asd.cms.model.TUser;

public interface HrDao {

	public List<String> getUniqueEmpId();
	public List<TExpense> getExpenseList(String empId);
	public boolean approveStatusDate(String id);
	public boolean SaveOrUpdate(TUser user);
	public List<TUser> viewUser();
	public TUser getUser(String id);
	public boolean deleteUser(String id);
	public boolean activateUser(String id);
	public boolean deactivateUser(String id);
	public List<TExpense> getDetailsInfo(long id);
	public TUser getEmployee(String empId);
	public TUser getEmployee(long empId);
	public List<TExpense> getConveyanceViewDetails(String dateFrom,String dateTo);
	
	////////////////////For Requisition     \\\\\\\\\\\\\\\\\\
	
	public List<String> getUniqueEmpIdReq();
	public List<TRequisition> getRequisitionList(String empId);
	public List<TRequisition> getDetailsInfoReq(long id);
	public boolean approveStatusDateReq(String id);
	public List<TRequisition> getRequisitionViewDetails(String dateFrom,String dateTo);
}
