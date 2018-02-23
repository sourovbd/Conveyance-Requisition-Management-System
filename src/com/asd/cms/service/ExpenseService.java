package com.asd.cms.service;

import java.util.Date;
import java.util.List;

import com.asd.cms.model.TAdminView;
import com.asd.cms.model.TExpense;

public interface ExpenseService {

	public boolean insert(TExpense expense);
	public List<TExpense> getEmployeeInfo(String loginId);
	public boolean delete(TExpense tExpense);
	public boolean updateStatusDate(long id);
	public List<TExpense> getConveyanceViewDetails(String dateFrom,String dateTo,String loginId);
	
}
