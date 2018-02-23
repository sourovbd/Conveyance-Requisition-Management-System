package com.asd.cms.dao;

import java.util.Date;
import java.util.List;

import com.asd.cms.model.TExpense;

public interface ExpenseDao {

	public boolean insert(TExpense expense);
	public List<TExpense> getEmployeeInfo(String loginId);
	public boolean delete(TExpense tExpense);
	public boolean updateStatusDate(long id);
	public List<TExpense> getConveyanceViewDetails(String dateFrom,String dateTo,String loginId);
}
