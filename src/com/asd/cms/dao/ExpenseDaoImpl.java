package com.asd.cms.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import com.asd.cms.model.TExpense;
import com.asd.cms.util.Utilities;

@Repository
public class ExpenseDaoImpl implements ExpenseDao {

	//@Autowired
	//@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	public Session session;
	public Transaction tx;
	public String sql = "";
	Query query = null;
	
	@Required
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public boolean insert(TExpense expense) {
		
		boolean retVal = false;
		session = sessionFactory.openSession(); 
		try {
			tx = session.beginTransaction();
			session.save(expense);
			retVal = true;
			session.getTransaction().commit();
			
		}catch(Exception e) {
			
			e.printStackTrace();
			retVal = false;
			session.getTransaction().rollback();
		}
		finally {
			
			session.flush();
			session.clear();
			session.close();
		}
		return retVal;
	}

	@Override
	public List<TExpense> getEmployeeInfo(String loginId) {
		
		session = sessionFactory.openSession(); 
		List<TExpense> expenseList = new ArrayList<TExpense>();
		try {
			
			tx = session.beginTransaction();
			expenseList =  session.createQuery("from TExpense where expenseEmployeeId = "+loginId+" and expenseStatus = "+0).list();
			session.getTransaction().commit();
			
		}catch(Exception e) {
			
			e.printStackTrace();
			session.getTransaction().rollback();			
		}
		finally {
			
			session.clear();
			session.flush();
			session.close();			
		}
		return expenseList;
	}

	@Override
	public boolean delete(TExpense tExpense) {
		
		session = sessionFactory.openSession();
		boolean retVal = false;
		try {
			
			tx = session.beginTransaction();
			session.delete(tExpense);
			retVal = true;
			tx.commit();
			
		}catch(Exception e) {
			
			e.printStackTrace();
			retVal = false;
			tx.rollback();
		}
		finally {
			
			session.flush();
			session.clear();
			session.close();
		}
		return retVal;
	}

	@Override
	public boolean updateStatusDate(long id) {
		
		session = sessionFactory.openSession();
		boolean retVal = true;
		try {
			
			tx = session.beginTransaction();	
			//System.out.println("id from ExpensedaoImpl: "+ id);
			session.createQuery("update TExpense set expenseStatus = "+ 1 +",expenseSubmitDate = '"+ Utilities.getCurrentDate() +"' where  expenseId = "+id).executeUpdate();
			//System.out.println("after query");
			retVal = true;
			tx.commit();
			
		}catch(Exception e) {
			
			e.printStackTrace();
			retVal = false;
			tx.rollback();
		}
		finally {
			
			session.clear();
			session.flush();
			session.close();
		}
		return retVal;
	}

	@Override
	public List<TExpense> getConveyanceViewDetails(String dateFrom,
			String dateTo, String loginId) {
		
		session = sessionFactory.openSession(); 
		List<TExpense> expenseList = new ArrayList<TExpense>();
		try {
			
			tx = session.beginTransaction();
			expenseList =  session.createQuery("from TExpense where expenseEmployeeId = "+loginId+" and expenseSubmitDate between '"+dateFrom+"' and '"+dateTo+"'").list();
			tx.commit(); 
			
		}catch(Exception e) {
			
			e.printStackTrace();
			tx.rollback();			
		}
		finally {
			
			session.flush();
			session.clear();
			session.close();			
		}
		return expenseList;
	}

}

