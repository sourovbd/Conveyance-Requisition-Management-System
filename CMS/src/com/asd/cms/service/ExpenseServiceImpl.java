package com.asd.cms.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asd.cms.dao.ExpenseDao;
import com.asd.cms.model.TExpense;

@Service
public class ExpenseServiceImpl implements ExpenseService {

	@Autowired
	private ExpenseDao expenseDao;
	
	@Override
	public boolean insert(TExpense expense) {

		return expenseDao.insert(expense);
	}

	@Override
	public List<TExpense> getEmployeeInfo(String loginId) {

		return expenseDao.getEmployeeInfo(loginId);
	}

	@Override
	public boolean delete(TExpense tExpense) {
		
		return expenseDao.delete(tExpense);
	}

	@Override
	public boolean updateStatusDate(long id) {
		
		return expenseDao.updateStatusDate((long)id);
	}

	@Override
	public List<TExpense> getConveyanceViewDetails(String dateFrom,
			String dateTo, String loginId) {
		
		return expenseDao.getConveyanceViewDetails(dateFrom, dateTo,loginId);
	}

}
