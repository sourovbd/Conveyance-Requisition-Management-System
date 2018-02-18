package com.asd.cms.service;

import java.util.List;

import com.asd.cms.model.TAdminView;
import com.asd.cms.model.TExpense;
import com.asd.cms.model.TRequisition;
import com.asd.cms.model.TUser;

public interface AdminService {

	public List<TExpense> getExpenseList(String empId);
	public List<String> getUniqueEmpId();
	public List<TExpense> getDetailsInfo(long id);
	public boolean approveStatusDate(Long id);
	public boolean rejectStatusDate(Long id);
	public List<TUser> getHrList();
	public List<TExpense> getConveyanceViewDetails(String dateFrom,String dateTo);
	///////////  For Requisition  \\\\\\\\\\\\
	public List<String> getUniqueEmpIdReq();
	public List<TRequisition> getRequisitionList(String empID);
	public List<TRequisition> getDetailsInfoReq(long id);
	public boolean approveStatusDateReq(Long id);
	public boolean rejectStatusDateReq(Long id);
	public List<TUser> getHrListReq();
	public List<TRequisition> getEntryViewDetailsReq(String reqDateFrom,String reqDateTo);
}
