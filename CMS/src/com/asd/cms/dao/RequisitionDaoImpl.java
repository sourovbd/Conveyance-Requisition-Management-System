package com.asd.cms.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import com.asd.cms.model.TExpense;
import com.asd.cms.model.TRequisition;
import com.asd.cms.util.Utilities;

@Repository
public class RequisitionDaoImpl implements RequisitionDao {

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
	public boolean insert(TRequisition requisition) {
		
		boolean retVal = false;
		session = sessionFactory.openSession(); 
		try {
			tx = session.beginTransaction();
			session.save(requisition);
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
	public List<TRequisition> getEmployeeInfo(String loginId) {
		
		session = sessionFactory.openSession(); 
		List<TRequisition> requisitionList = new ArrayList<TRequisition>();
		try {
			
			tx = session.beginTransaction();
			requisitionList =  session.createQuery("from TRequisition where reqEmployeeId = "+loginId+" and reqStatus = "+0).list();
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
		return requisitionList;
	}
	@Override
	public boolean updateStatusDate(long id) {
		
		session = sessionFactory.openSession();
		boolean retVal = true;
		try {
			
			tx = session.beginTransaction();	
			//System.out.println("id from ExpensedaoImpl: "+ id);
			session.createQuery("update TRequisition set reqStatus = "+ 1 +",reqSubmitDate = '"+ Utilities.getCurrentDate() +"' where  reqId = "+id).executeUpdate();
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
	public boolean delete(TRequisition requisition) {
		
		session = sessionFactory.openSession();
		boolean retVal = false;
		try {
			
			tx = session.beginTransaction();
			session.delete(requisition);
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
	public List<TRequisition> getRequisitionViewDetails(String reqDateFrom,
			String reqDateTo, String loginId) {
		
		session = sessionFactory.openSession(); 
		List<TRequisition> requisitionList = new ArrayList<TRequisition>();
		try {
			
			tx = session.beginTransaction();
			requisitionList =  session.createQuery("from TRequisition where reqEmployeeId = "+loginId+" and reqSubmitDate between '"+reqDateFrom+"' and '"+reqDateTo+"'").list();
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
		return requisitionList;
	}
}
